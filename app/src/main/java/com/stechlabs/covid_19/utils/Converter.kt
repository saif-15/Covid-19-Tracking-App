package com.stechlabs.covid_19.utils

import com.stechlabs.covid_19.models.apiResponse.Country
import com.stechlabs.covid_19.models.persistence.Country as db_country
import retrofit2.Response

object Converter {

    fun getCountryList(old_list:List<Country>):List<db_country>{
        val new_list:ArrayList<db_country> = ArrayList()
        for(i in 0 until old_list.size){
            val old=old_list[i]
            new_list.add(db_country(
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
        return new_list
    }
    fun getCountryObject(old:Response<Country>):db_country{
        val country=old.body()!!
        return db_country(
            country=country.country,
            cases=country.cases,
            todayCases = country.todayCases,
            deaths = country.deaths,
            todayDeaths = country.todayDeaths,
            recovered = country.recovered,
            active = country.active,
            critical = country.critical,
            casesPerOneMillion = country.casesPerOneMillion,
            deathsPerOneMillion = country.deathsPerOneMillion,
            totalTests = country.totalTests,
            testsPerOneMillion = country.testsPerOneMillion

        )
    }
}