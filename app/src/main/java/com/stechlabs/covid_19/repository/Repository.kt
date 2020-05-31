package com.stechlabs.covid_19.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.stechlabs.covid_19.ApiService.MyRetrofitBuilder
import com.stechlabs.covid_19.models.persistence.Country
import com.stechlabs.covid_19.persistence.Dao.CountryDao
import com.stechlabs.covid_19.persistence.database.MyDatabase
import com.stechlabs.covid_19.utils.Converter
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

class Repository(application: Application) {

    // vars And vals
    private var countryDao: CountryDao
    private lateinit var job: CompletableJob
    private val TIME_OUT = 4000L


    //  Intializing database and getting Dao
    init {
        val database = MyDatabase.getInstance(application.applicationContext)!!
        countryDao = database.countryDao()
    }


    //Creating singleton object of the repository
    companion object {
        private lateinit var repository: Repository
        fun getInstance(application: Application): Repository? {
            if (!::repository.isInitialized)
                repository = Repository(application)
            println("repo object:${repository.hashCode()}")
            return repository

        }
    }

    // Getting All countries results

    fun getCountries(): LiveData<List<Country>> {
        job = Job()
        return object : LiveData<List<Country>>() {
            override fun onActive() {
                super.onActive()
                job.let {
                    CoroutineScope(IO).launch {
                        delay(3000)
                        val temp = countryDao.getAllResults()
                        postValue(temp)
                        job.complete()
                    }
                }
            }
        }
    }


    // caching Data to the database
    private suspend fun cacheCountryData(list: List<Country>) {
        job = Job()
        job.let {
            countryDao.cacheCountryData(list = list)
            println("Debug:caching data")
            job.complete()
        }
    }

    // getting global Result

    fun getGlobalResult(): LiveData<Country> {
        job = Job()
        return object : LiveData<Country>() {
            override fun onActive() {
                super.onActive()
                job.let {
                    CoroutineScope(IO).launch {

                        /* Requesting internet for 4 seconds for data
                         if timeout occurs data is loaded from the
                         database
                      */
                        val job2 = withTimeoutOrNull(TIME_OUT) {
                            try {
                                val temp = MyRetrofitBuilder.ApiService.getAllResults().body()!!
                                println("Debug Network request....")
                                val data = async { Converter.getCountryList(temp) }
                                cacheCountryData(data.await())
                            } catch (ex: Exception) {
                                println("debug  ${ex.message}")
                            } finally {
                                // if Exceptions occurs
                                postValue(countryDao.getGlobalResult())
                                println("Debug Getting data from Database.... in finally")
                                job.complete()
                            }
                        }
                        if (job2 == null) {
                            // if timeout occurs
                            postValue(countryDao.getGlobalResult())
                            println("Debug Getting data from Database....job2 is null")
                        }

                    }
                }
            }
        }
    }


    fun getTop10Countries(): LiveData<List<Country>> {
        job = Job()
        return object : LiveData<List<Country>>() {
            override fun onActive() {
                super.onActive()
                job.let {
                    CoroutineScope(IO).launch {
                        val list = countryDao.getTop10Countries()
                        postValue(list)
                        job.complete()
                    }
                }
            }
        }
    }

    fun getBottom10Countries(): LiveData<List<Country>> {
        job = Job()
        return object : LiveData<List<Country>>() {
            override fun onActive() {
                super.onActive()
                job.let {
                    CoroutineScope(IO).launch {
                        val temp = countryDao.getBottom10Countries()
                        postValue(temp)
                        job.complete()
                    }
                }
            }
        }

    }


    fun searchCountry(query: String): LiveData<List<Country>> {
        job = Job()
        return object : LiveData<List<Country>>() {
            override fun onActive() {
                super.onActive()
                job.let {
                    CoroutineScope(IO).launch {
                        val temp = countryDao.searchQuery(query)
                        job.complete()
                        withContext(Main) {
                            value = temp
                        }
                    }
                }
            }
        }
    }


    fun getCountriesByDeathsToday(): LiveData<List<Country>> {
        job = Job()
        return object : LiveData<List<Country>>() {
            override fun onActive() {
                super.onActive()
                job.let {
                    CoroutineScope(IO).launch {
                        val temp = countryDao.getCountriesByDeathsToday()
                        job.complete()
                        withContext(Main) {
                            value = temp
                        }
                    }
                }

            }
        }

    }

    fun getCountriesByDeaths(): LiveData<List<Country>> {
        job = Job()
        return object : LiveData<List<Country>>() {
            override fun onActive() {
                super.onActive()
                job.let {
                    CoroutineScope(IO).launch {
                        val temp = countryDao.getCountriesByDeaths()
                        println("$temp  in repo")
                        postValue(temp)
                        job.complete()
                    }
                }

            }
        }

    }

    fun getCountriesByTodayCases(): LiveData<List<Country>> {
        job = Job()
        return object : LiveData<List<Country>>() {
            override fun onActive() {
                super.onActive()
                job.let {
                    CoroutineScope(IO).launch {
                        val temp = countryDao.getCountriesByTodayCases()
                        job.complete()
                        withContext(Main) {
                            value = temp
                        }
                    }
                }
            }
        }
    }

    fun getCountriesByTests(): LiveData<List<Country>> {
        job = Job()
        return object : LiveData<List<Country>>() {
            override fun onActive() {
                super.onActive()
                job.let {
                    CoroutineScope(IO).launch {
                        val temp = countryDao.getCountriesByTests()
                        job.complete()
                        withContext(Main) {
                            value = temp
                        }
                    }
                }

            }
        }
    }

    fun getCountriesByRecovered(): LiveData<List<Country>> {
        job = Job()
        return object : LiveData<List<Country>>() {
            override fun onActive() {
                super.onActive()
                job.let {
                    CoroutineScope(IO).launch {
                        val list = countryDao.getCountriesByRecovered()
                        postValue(list)
                        job.complete()
                    }
                }
            }
        }
    }

    fun cancelJobs() {
        job.cancel()
    }
}
