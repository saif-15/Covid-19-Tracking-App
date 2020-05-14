package com.stechlabs.covid_19.ui.symptoms

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.stechlabs.covid_19.repository.Repository

class SymptomsViewModel(application: Application) : AndroidViewModel(application) {

    private var repository = Repository.getInstance(application)

    fun cancelJobs(){
        repository!!.cancelJobs()
    }
}