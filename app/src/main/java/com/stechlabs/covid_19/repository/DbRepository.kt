package com.stechlabs.covid_19.repository

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stechlabs.covid_19.Persistence.Dao.CountryDao
import com.stechlabs.covid_19.Persistence.Dao.GlobalDao
import com.stechlabs.covid_19.Persistence.database.MyDatabase
import com.stechlabs.covid_19.models.persistence.Country
import com.stechlabs.covid_19.models.persistence.Global
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

class DbRepository(application: Application) {

    private var _countryDao:CountryDao
    private var _globalDao:GlobalDao
    private lateinit var job:CompletableJob
    private var _countryList:MutableLiveData<List<Country>> = MutableLiveData()
    private var _globalList:MutableLiveData<Global> = MutableLiveData()
    private var _country:MutableLiveData<Country> = MutableLiveData()

    init {
        val database=MyDatabase.getInstance(application.applicationContext)!!
        _countryDao=database._countryDao()
        _globalDao=database._globalDao()
    }

    fun cacheCountryData(list:List<Country>):Boolean{
        var isCached=true
        job= Job()
        job.let {
            CoroutineScope(IO+it).launch {
                _countryDao.cacheCountryData(list=list)
                job.invokeOnCompletion {
                    if(it != null)
                      isCached=false
                }
            }
        }
        return isCached
    }
    fun cacheGlobalData(global: Global):Boolean{
        var isCached=true
        job= Job()
        job.let {
            CoroutineScope(IO+it).launch {
                _globalDao.caheGLobalData(global = global)
                job.invokeOnCompletion {
                    if(it != null)
                        isCached=false
                }
            }
        }
        return isCached
    }

    fun getAllResults(){
        job=Job()
        job.let {
            CoroutineScope(IO+it).launch {
                val list=_countryDao.getAllResults()
                    job.complete()
                withContext(Main){
                    _countryList.value=list
                }
            }
        }
    }
    fun getGlobalResult(){
        job= Job().apply {
            CoroutineScope(IO+this).launch {
                val global=_globalDao.getGlobalResults()
                    job.complete()
                withContext(Main){
                    _globalList.postValue(global)
                }
            }
        }
    }
    fun getCountryByName(Country:String){
        job= Job()
        job.let {
            CoroutineScope(IO+it).launch {
                val country=_countryDao.getCountryResult(Country)
                job.complete()
                withContext(Main){
                 _country.postValue(country)
                }

            }
        }
    }

    // Observe By the ViewModels from Room

    fun observeCountries():LiveData<List<Country>>{
        getAllResults()
        return _countryList
    }
    fun observeGlobal():LiveData<Global>{
        getGlobalResult()
        return _globalList
    }
    fun observerCountry(Country: String):LiveData<Country>{
        getCountryByName(Country)
        return _country
    }

    fun cancelJobs()
    {
        job.cancel()
    }

}