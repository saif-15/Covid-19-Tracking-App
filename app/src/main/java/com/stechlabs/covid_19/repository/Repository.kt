package com.stechlabs.covid_19.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stechlabs.covid_19.ApiService.MyRetofitBuilder
import com.stechlabs.covid_19.models.apiResponse.Country as api
import com.stechlabs.covid_19.models.persistence.Country
import com.stechlabs.covid_19.models.apiResponse.Global
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import retrofit2.Response

object Repository {

   /* private  var mutableLiveData_global:Response<Global> = */
    private var mutableLiveData_countries:List<api> = ArrayList()


    var  job:CompletableJob?=null

/*    private fun getGlobalResults(){
        job= Job()

        job.let {
            CoroutineScope(IO).launch {
                val temp=MyRetofitBuilder.ApiService.getGlobalResult()
                job?.complete()
                withContext(Main){
                    mutableLiveData_global=temp
                }
            }
        }
    }*/

    /*fun observeGlobal():Response<Global>{
        getGlobalResults()
        return mutableLiveData_global
    }*/

    private fun getCountriesResults(){
        job= Job()

        job.let {
            CoroutineScope(IO).launch {
                val temp=MyRetofitBuilder.ApiService.getAllResults().body()!!
                job?.complete()
                withContext(Main){
                    mutableLiveData_countries=temp
                }
            }
        }
    }

    fun observeCountries():List<api>{
        getCountriesResults()
        return mutableLiveData_countries
    }

    fun cancelJobs(){
        job?.cancel()
    }
}