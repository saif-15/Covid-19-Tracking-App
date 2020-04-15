package com.stechlabs.covid_19.utils

import com.stechlabs.covid_19.models.apiResponse.Country
import com.stechlabs.covid_19.models.persistence.Country as db_country

object Converter {

    suspend fun getCountryList(old_list: List<Country>): List<db_country> {
        val newList: ArrayList<db_country> = ArrayList()
        for(i in 0 until old_list.size){
            val old = old_list[i]
            newList.add(
                db_country(
                country=old.country,
                cases=old.cases,
                todayCases = old.todayCases,
                deaths = old.deaths,
                todayDeaths = old.todayDeaths,
                recovered = old.recovered,
                active = old.active,
                critical = old.critical,
                casesPerOneMillion = old.casesPerOneMillion,
                deathsPerOneMillion = old.deathsPerOneMillion,
                totalTests = old.totalTests,
                testsPerOneMillion = old.testsPerOneMillion
            ))
        }
        return newList
    }
}