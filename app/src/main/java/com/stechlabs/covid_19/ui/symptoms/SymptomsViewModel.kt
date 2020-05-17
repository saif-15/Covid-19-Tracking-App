package com.stechlabs.covid_19.ui.symptoms

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stechlabs.covid_19.R
import com.stechlabs.covid_19.models.ui.Items

class SymptomsViewModel : ViewModel() {

    private val _symptomsList = MutableLiveData<List<Items>>()
    private val _preventionsList = MutableLiveData<List<Items>>()


    init {
        _symptomsList.value = populateSymptomsList()
        _preventionsList.value = populatePreventionsList()
    }

    private fun populateSymptomsList(): List<Items> {
        return listOf(
            Items("Sneeze", "", R.raw.sneezing),
            Items("Temperature", "", R.raw.temperature),
            Items("Breathe", "", R.raw.lungs),
            Items("Fever", "", R.raw.fever)
        )
    }

    private fun populatePreventionsList(): List<Items> {
        return listOf(
            Items("Wear Mask", "", R.raw.mask),
            Items("Wash Hand", "", R.raw.handwashing),
            Items("Distancing", "", R.raw.distancing),
            Items("Stay Home", "", R.raw.stayhome)
        )
    }

    fun observeSymptomsList() = _symptomsList
    fun observePreventionsList() = _preventionsList
}