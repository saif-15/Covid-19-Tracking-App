package com.stechlabs.covid_19.persistence.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import com.stechlabs.covid_19.models.persistence.Country

@Dao
interface CountryDao {

    @Insert(onConflict = REPLACE)
    suspend fun cacheCountryData(list: List<Country>)


    @Query(
        "SELECT * FROM country as c WHERE c.country!='World' " +
                "AND c.country!='Europe'  AND c.country!='North America' " +
                "AND c.country !='Asia'  AND c.country !='South America' " +
                "ORDER BY cases DESC"
    )
    suspend fun getAllResults(): List<Country>

    @Transaction
    @Query("SELECT * FROM country as c WHERE c.country = 'World' ORDER BY date DESC")
    suspend fun getGlobalResult(): Country

    @Transaction
    @Query(
        "SELECT * FROM country as c WHERE c.country!='World' AND c.country!='Europe' " +
                "AND c.country!='North America' AND c.country !='Asia'  AND " +
                "c.country !='South America' ORDER BY cases DESC LIMIT 10"
    )
    suspend fun getTop10Countries(): List<Country>


    @Transaction
    @Query(
        "SELECT * FROM country as c WHERE c.country!='World' AND c.country!='Europe' " +
                "AND c.country!='North America' AND c.country !='Asia'  AND " +
                "c.country !='South America' ORDER BY cases ASC LIMIT 10"
    )
    suspend fun getBottom10Countries(): List<Country>


    @Transaction
    @Query(
        "SELECT * FROM country as c WHERE c.country !='World' AND " +
                "c.country !='Europe' AND c.country !='North America'  " +
                "AND c.country !='Asia'  AND c.country !='South America' " +
                "AND c.country LIKE '%' || :query || '%' "
    )
    suspend fun searchQuery(query: String): List<Country>

    @Transaction
    @Query(
        "SELECT * FROM country as c WHERE c.country !='World' AND " +
                "c.country !='Europe' AND c.country !='North America'  " +
                "AND c.country !='Asia'  AND c.country !='South America' " +
                "ORDER BY todayCases DESC LIMIT 10"
    )
    suspend fun getCountriesByTodayCases(): List<Country>


    @Transaction
    // Also group deathsPerOneMllion
    @Query(
        "SELECT * FROM country as c WHERE c.country !='World' AND " +
                "c.country !='Europe' AND c.country !='North America' " +
                "AND c.country !='Asia'  AND c.country !='South America' " +
                "ORDER BY deaths DESC LIMIT 10"
    )
    suspend fun getCountriesByDeaths(): List<Country>

    @Transaction
    @Query(
        "SELECT * FROM country as c WHERE c.country !='World' AND " +
                "c.country !='Europe' AND c.country !='North America'  " +
                "AND c.country !='Asia'  AND c.country !='South America' " +
                "ORDER BY todayDeaths DESC LIMIT 10"
    )
    suspend fun getCountriesByDeathsToday(): List<Country>

    @Transaction
    @Query(
        "SELECT * FROM country as c WHERE c.country !='World' AND " +
                "c.country !='Europe' AND c.country !='North America'  " +
                "AND c.country !='Asia'  AND c.country !='South America' " +
                "ORDER BY totalTests DESC LIMIT 10"
    )
    suspend fun getCountriesByTests(): List<Country>


    @Transaction
    @Query(
        "SELECT * FROM country as c WHERE c.country !='World' AND " +
                "c.country !='Europe' AND c.country !='North America'  " +
                "AND c.country !='Asia'  AND c.country !='South America' " +
                "ORDER BY recovered DESC LIMIT 10"
    )
    suspend fun getCountriesByRecovered(): List<Country>


}