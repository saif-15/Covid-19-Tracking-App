package com.stechlabs.covid_19.utils

import com.stechlabs.covid_19.models.apiResponse.Country
import com.stechlabs.covid_19.models.persistence.Country as db_country

object Converter {

    fun getCountryList(old_list: List<Country>): List<db_country> {
        val newList: ArrayList<db_country> = ArrayList()
        for (element in old_list) {
            newList.add(
                db_country(
                    country = element.country,
                    cases = element.cases,
                    todayCases = element.todayCases,
                    deaths = element.deaths,
                    todayDeaths = element.todayDeaths,
                    recovered = element.recovered,
                    active = element.active,
                    critical = element.critical,
                    casesPerOneMillion = element.casesPerOneMillion,
                    deathsPerOneMillion = element.deathsPerOneMillion,
                    totalTests = element.totalTests,
                    testsPerOneMillion = element.testsPerOneMillion
            ))
        }
        return newList
    }
}