package com.stechlabs.covid_19.ui.listing

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.stechlabs.covid_19.models.persistence.Country
import com.stechlabs.covid_19.repository.Repository

class ListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = Repository.getInstance(application)!!

    fun getCountries(): LiveData<List<Country>> {
        return repository.getCountries()
    }

    fun getCountriesByQuery(query: String): LiveData<List<Country>> {
        return repository.searchCountry(query)
    }

    fun cancelJobs() {
        repository.cancelJobs()
    }
}