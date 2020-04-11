package com.stechlabs.covid_19.repository

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.lifecycle.LiveData
import com.stechlabs.covid_19.models.persistence.Country
import com.stechlabs.covid_19.utils.Converter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainRepository(private val application: Application) {

    fun isNetworkAvailablity():Boolean{
        val manager=application.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE)
                    as ConnectivityManager
        val networkInfo:NetworkInfo? = manager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    fun loadAllCountryData():LiveData<List<Country>>{
        val repository=DbRepository(application)
        if(isNetworkAvailablity()){
            CoroutineScope(IO).launch {
                val list = Converter.getCountryList(Repository.observeCountries())
                repository.cacheCountryData(list)
            }
        }
        return repository.observeCountries()
    }
}