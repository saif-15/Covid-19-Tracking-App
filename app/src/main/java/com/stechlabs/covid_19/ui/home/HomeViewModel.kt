package com.stechlabs.covid_19.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.stechlabs.covid_19.models.apiResponse.Country
import com.stechlabs.covid_19.repository.Repository
import retrofit2.Response

class HomeViewModel : ViewModel() {

    fun getGlobalResults():LiveData<Response<List<Country>>> = Repository.observeCountries()
    fun cancelJobs(){
        Repository.cancelJobs()
    }
}