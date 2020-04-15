package com.stechlabs.covid_19.models.persistence

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "country")
data class Country(

    @PrimaryKey
    val country:String,
    val cases:Int,
    val todayCases:Int,
    val deaths:Int,
    val todayDeaths:Int,
    val recovered:Int,
    val active:Int,
    val critical:Int,
    val casesPerOneMillion:Int,
    val deathsPerOneMillion:Int,
    val totalTests:Int,
    val testsPerOneMillion:Int
)