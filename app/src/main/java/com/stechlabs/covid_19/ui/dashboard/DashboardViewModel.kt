package com.stechlabs.covid_19.ui.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stechlabs.covid_19.models.persistence.Country
import com.stechlabs.covid_19.repository.Repository

class DashboardViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = Repository.getInstance(application)!!
    private var list = MutableLiveData<List<Country>>()
    private var searched_list = MutableLiveData<List<Country>>()

    fun getCountriesResultFromDB(): LiveData<List<Country>> {
        list = repository.observeCountriesFromDB()
        return list
    }

    fun getSearchedCountries(query: String): LiveData<List<Country>> {
        searched_list = repository.searchCountry(query)
        println("data ${searched_list.value}")
        return searched_list
    }

    fun cancelJobs() {
        repository.cancelJobs()
    }
}