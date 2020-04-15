package com.stechlabs.covid_19.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.stechlabs.covid_19.models.persistence.Country
import com.stechlabs.covid_19.repository.Repository

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private var repository = Repository.getInstance(application)


    fun getCountriesResultFromDB(): LiveData<List<Country>> =
        repository!!.observeCountriesFromDB()

    fun cancelJobs(){
        repository!!.cancelJobs()
    }
}