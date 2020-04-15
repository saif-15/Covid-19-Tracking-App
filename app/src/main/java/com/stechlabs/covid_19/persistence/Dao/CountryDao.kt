package com.stechlabs.covid_19.persistence.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.stechlabs.covid_19.models.persistence.Country

@Dao
interface CountryDao {

    @Insert(onConflict = REPLACE)
    suspend fun cacheCountryData(list:List<Country>)

    @Query("SELECT * FROM country ORDER BY cases DESC")
    suspend fun getAllResults():List<Country>

    @Query("SELECT * FROM country as c WHERE c.country = :countryName")
    suspend fun getCountryResult(countryName:String):Country

    @Query("SELECT * FROM country as c WHERE c.country = 'World'")
    suspend fun getGlobalResult(): Country

}