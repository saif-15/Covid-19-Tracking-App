package com.stechlabs.covid_19.Persistence.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.stechlabs.covid_19.models.persistence.Country

@Dao
interface CountryDao {

    @Insert
    suspend fun caheCountryData(list:List<Country>)

    @Query("SELECT * FROM country ORDER BY cases DESC")
    suspend fun getAllResults():List<Country>

    @Query("SELECT * FROM country WHERE country.country == :countryName")
    suspend fun getCountryResult(countryName:String):Country
}