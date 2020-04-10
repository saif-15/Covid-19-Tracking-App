package com.stechlabs.covid_19.repository

import android.app.Application
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
    private var _country_list:MutableLiveData<List<Country>> = MutableLiveData()
    private var _global_list:MutableLiveData<List<Global>> = MutableLiveData()

    init {
        val database=MyDatabase.getInstance(application.applicationContext)!!
        _countryDao=database._countryDao()
        _globalDao=database._globalDao()
    }

    fun cacheCountryData(list:List<Country>){
        job= Job()
        job.let {
            CoroutineScope(IO+it).launch {
                _countryDao.caheCountryData(list)
                if(!job.completeExceptionally(Throwable("Exception in caching..")))
                    job.complete()
            }
        }
    }

    fun getAllResults(){
        job=Job()
        job.let {
            CoroutineScope(IO+it).launch {
                var list=_countryDao.getAllResults()
                if(!job.completeExceptionally(Throwable("Exception in caching..")))
                    job.complete()
                withContext(Main){
                    _country_list.value=list
                }
            }
        }
    }
}