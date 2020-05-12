package com.stechlabs.covid_19.ui.symptoms

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stechlabs.covid_19.models.persistence.Country
import com.stechlabs.covid_19.repository.Repository

class SymptomsViewModel(application: Application) : AndroidViewModel(application) {

    private var repository = Repository.getInstance(application)
    private var list = MutableLiveData<List<Country>>()

    fun getCountriesResultFromDB(): LiveData<List<Country>> {
        list = repository!!.observeCountriesFromDB()
        return list
    }

    fun cancelJobs(){
        repository!!.cancelJobs()
    }
}