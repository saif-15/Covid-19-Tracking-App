package com.stechlabs.covid_19.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.stechlabs.covid_19.models.apiResponse.Global
import com.stechlabs.covid_19.repository.Repository
import retrofit2.Response

class DashboardViewModel : ViewModel() {

    fun getGlobalResults():LiveData<Response<Global>> = Repository.observeGlobal()
    fun cancelJob(){
        Repository.cancelJobs()
    }
}