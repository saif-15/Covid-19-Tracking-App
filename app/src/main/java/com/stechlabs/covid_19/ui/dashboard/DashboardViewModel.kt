package com.stechlabs.covid_19.ui.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.stechlabs.covid_19.models.persistence.Country
import com.stechlabs.covid_19.repository.Repository

class DashboardViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = Repository.getInstance(application)!!

    fun getGlobalResult(): LiveData<Country> {
        return repository.getGlobalResult()
    }

    fun getTop10Countries(): LiveData<List<Country>> {
        return repository.getTop10Countries()
    }

    fun getBottom10Countries(): LiveData<List<Country>> {
        return repository.getBottom10Countries()
    }

    fun getCountriesByDeathsToday(): LiveData<List<Country>> {
        return repository.getCountriesByDeathsToday()
    }

    fun getCountriesByDeaths(): LiveData<List<Country>> {
        return repository.getCountriesByDeaths()
    }

    fun getCountriesByTodayCases(): LiveData<List<Country>> {
        return repository.getCountriesByTodayCases()
    }

    fun getCountriesByTests(): LiveData<List<Country>> {
        return repository.getCountriesByTests()
    }

    fun getCountriesByRecovered(): LiveData<List<Country>> {
        return repository.getCountriesByRecovered()
    }

    fun getCountries(): LiveData<List<Country>> {
        return repository.getCountries()
    }

    fun cancelJobs() {
        repository.cancelJobs()
    }
}