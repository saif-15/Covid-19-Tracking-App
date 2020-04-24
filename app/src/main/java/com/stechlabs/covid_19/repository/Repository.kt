package com.stechlabs.covid_19.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stechlabs.covid_19.ApiService.MyRetrofitBuilder
import com.stechlabs.covid_19.persistence.Dao.CountryDao
import com.stechlabs.covid_19.persistence.database.MyDatabase
import com.stechlabs.covid_19.utils.Converter
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import com.stechlabs.covid_19.models.persistence.Country as dbResponse

class Repository(application: Application) {

    private var countryDao: CountryDao
    private var listCountries1: MutableLiveData<List<dbResponse>> = MutableLiveData()
    private var country: MutableLiveData<dbResponse> = MutableLiveData()
    private var job: CompletableJob? = null
    private val TIME_OUT = 4000L

    init {
        val database = MyDatabase.getInstance(application.applicationContext)!!
        countryDao = database._countryDao()
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

    private fun getCountriesResults() {
        job = Job()
        job.let {
            CoroutineScope(IO).launch {
                val job2 = withTimeoutOrNull(TIME_OUT) {
                    try {
                        val temp = MyRetrofitBuilder.ApiService.getAllResults().body()!!
                        println("Debug Network request....")
                        val data = async { Converter.getCountryList(temp) }
                        cacheCountryData(data.await())
                        job?.complete()
                    } catch (ex: Exception) {
                        println(ex.message)
                    } finally {
                        loadData()
                    }
                }
                if (job2 == null) {
                    loadData()
                }
            }
        }
    }


    private fun cacheCountryData(list: List<dbResponse>) {
        job = Job()
        job?.let {
            CoroutineScope(IO + it).launch {
                countryDao.cacheCountryData(list = list)
                println("Debug:caching data")
                job?.complete()
            }
        }
    }

    fun observeCountriesFromDB(): LiveData<List<dbResponse>> {
        getCountriesResults()
        return listCountries1
    }

    fun observeGlobalResult(): LiveData<dbResponse> {
        getGlobalResult()
        return country
    }

    private fun getGlobalResult() {
        job = Job()
        job?.let {
            CoroutineScope(IO + it).launch {
                val temp = countryDao.getGlobalResult()
                job?.complete()
                withContext(Main) {
                    country.value = temp
                }
            }
        }
    }

    private suspend fun loadData() {
        withContext(IO) {
            listCountries1.postValue(countryDao.getAllResults())
            println("Debug:getting data from database")
        }
    }

    fun getTop10Countries(): LiveData<List<dbResponse>> {
        job = Job()
        val top10_list = MutableLiveData<List<dbResponse>>()
        job.let {
            CoroutineScope(IO).launch {
                val temp = countryDao.getTop10Countries()
                job!!.complete()
                withContext(Main) {
                    top10_list.postValue(temp)

                }
            }
        }
        return top10_list
    }

    fun getBottom10Countries(): LiveData<List<dbResponse>> {
        job = Job()
        val bottom10_list = MutableLiveData<List<dbResponse>>()
        job.let {
            CoroutineScope(IO).launch {
                val temp = countryDao.getBottom10Countries()
                job!!.complete()
                withContext(Main) {
                    bottom10_list.postValue(temp)

                }
            }
        }
        return bottom10_list
    }

    fun searchCountry(query: String): LiveData<List<dbResponse>> {

        job = Job()
        val query_list = MutableLiveData<List<dbResponse>>()
        job.let {
            CoroutineScope(IO).launch {
                val temp = countryDao.searchQuery(query)
                job!!.complete()
                withContext(Main) {
                    query_list.postValue(temp)

                }
            }
        }
        return query_list
    }
    fun cancelJobs() {
        job?.cancel()
    }
}