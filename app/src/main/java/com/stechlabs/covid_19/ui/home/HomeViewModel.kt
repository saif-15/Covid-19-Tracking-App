package com.stechlabs.covid_19.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.stechlabs.covid_19.models.persistence.Country
import com.stechlabs.covid_19.repository.DbRepository
import com.stechlabs.covid_19.repository.MainRepository
import com.stechlabs.covid_19.repository.Repository

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private var repository=MainRepository(application)

    fun getGlobalResults():LiveData<List<Country>> = repository.loadAllCountryData()
    fun cancelJobs(){
        Repository.cancelJobs()
    }
}