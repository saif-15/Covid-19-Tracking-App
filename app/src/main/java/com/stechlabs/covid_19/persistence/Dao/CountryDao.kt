package com.stechlabs.covid_19.persistence.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.stechlabs.covid_19.models.persistence.Country

@Dao
interface CountryDao {

    @Insert(onConflict = REPLACE)
    suspend fun cacheCountryData(list: List<Country>)

    @Query(
        "SELECT * FROM country as c WHERE c.country!='World' " +
                "AND c.country!='Europe' AND c.country!='North America' " +
                "AND c.country !='Asia'  AND c.country !='South America'" +
                "ORDER BY cases DESC"
    )
    suspend fun getAllResults(): List<Country>


    @Query("SELECT * FROM country as c WHERE c.country = 'World'")
    suspend fun getGlobalResult(): Country

    @Query(
        "SELECT * FROM country as c WHERE c.country!='World' AND c.country!='Europe' " +
                "AND c.country!='North America' AND c.country !='Asia'  AND " +
                "c.country !='South America' ORDER BY cases DESC LIMIT 10"
    )
    suspend fun getTop10Countries(): List<Country>


    @Query(
        "SELECT * FROM country as c WHERE c.country!='World' AND c.country!='Europe' " +
                "AND c.country!='North America' AND c.country !='Asia'  AND " +
                "c.country !='South America' ORDER BY cases ASC LIMIT 10"
    )
    suspend fun getBottom10Countries(): List<Country>


    @Query(
        "SELECT * FROM country as c WHERE c.country !='World' AND " +
                "c.country !='Europe' AND c.country !='North America'  " +
                "AND c.country !='Asia'  AND c.country !='South America' " +
                "AND c.country LIKE '%' || :query || '%' "
    )
    suspend fun searchQuery(query: String): List<Country>


    @Query(
        "SELECT * FROM country as c WHERE c.country !='World' AND " +
                "c.country !='Europe' AND c.country !='North America'  " +
                "AND c.country !='Asia'  AND c.country !='South America' " +
                "AND c.country ORDER BY todayCases DESC LIMIT 10"
    )
    suspend fun getCountriesByTodayCases(): List<Country>


    // Also group deathsPerOneMllion
    @Query(
        "SELECT * FROM country as c WHERE c.country !='World' AND " +
                "c.country !='Europe' AND c.country !='North America'  " +
                "AND c.country !='Asia'  AND c.country !='South America' " +
                "AND c.country ORDER BY deaths DESC LIMIT 10"
    )
    suspend fun getCountriesByDeaths(): List<Country>


    @Query(
        "SELECT * FROM country as c WHERE c.country !='World' AND " +
                "c.country !='Europe' AND c.country !='North America'  " +
                "AND c.country !='Asia'  AND c.country !='South America' " +
                "AND c.country ORDER BY todayDeaths DESC LIMIT 10"
    )
    suspend fun getCountriesByDeathsToday(): List<Country>

    @Query(
        "SELECT * FROM country as c WHERE c.country !='World' AND " +
                "c.country !='Europe' AND c.country !='North America'  " +
                "AND c.country !='Asia'  AND c.country !='South America' " +
                "AND c.country ORDER BY totalTests DESC LIMIT 10"
    )
    suspend fun getCountriesByTests(): List<Country>
}