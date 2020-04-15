package com.stechlabs.covid_19.ui.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.stechlabs.covid_19.models.persistence.Country
import com.stechlabs.covid_19.repository.Repository

class DashboardViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = Repository.getInstance(application)!!

    fun getGlobalResult(): LiveData<Country> = repo.observeGlobalResult()

    fun cancelJob(){
        repo.cancelJobs()
    }
}