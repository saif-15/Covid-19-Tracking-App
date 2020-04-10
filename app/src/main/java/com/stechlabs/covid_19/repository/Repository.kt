package com.stechlabs.covid_19.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stechlabs.covid_19.ApiService.MyRetofitBuilder
import com.stechlabs.covid_19.models.apiResponse.Country
import com.stechlabs.covid_19.models.apiResponse.Global
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import retrofit2.Response

object Repository {

    private val mutableLiveData_global:MutableLiveData<Response<Global>> = MutableLiveData()
    private val mutableLiveData_countries:MutableLiveData<Response<List<Country>>> = MutableLiveData()


    var  job:CompletableJob?=null

    private fun getGlobalResults(){
        job= Job()

        job.let {
            CoroutineScope(IO).launch {
                val temp=MyRetofitBuilder.ApiService.getGlobalResult()
                job?.complete()
                withContext(Main){
                    mutableLiveData_global.postValue(temp)
                }
            }
        }
    }

    fun observeGlobal():LiveData<Response<Global>>{
        getGlobalResults()
        return mutableLiveData_global
    }

    private fun getCountriesResults(){
        job= Job()

        job.let {
            CoroutineScope(IO).launch {
                val temp=MyRetofitBuilder.ApiService.getAllResults()
                job?.complete()
                withContext(Main){
                    mutableLiveData_countries.postValue(temp)
                }
            }
        }
    }

    fun observeCountries():LiveData<Response<List<Country>>>{
        getCountriesResults()
        return mutableLiveData_countries
    }

    fun cancelJobs(){
        job?.cancel()
    }
}