package com.stechlabs.covid_19.models.apiResponse

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Country(

    @SerializedName("country") @Expose
    val country:String,
    @SerializedName("cases") @Expose val
    cases:Int,
    @SerializedName("todayCases") @Expose
    val todayCases:Int,
    @SerializedName("deaths") @Expose
    val deaths:Int,
    @SerializedName("todayDeaths") @Expose
    val todayDeaths:Int,
    @SerializedName("recovered") @Expose
    val recovered:Int,
    @SerializedName("active") @Expose
    val active:Int,
    @SerializedName("critical") @Expose
    val critical:Int,
    @SerializedName("casesPerOneMillion") @Expose
    val casesPerOneMillion:Int,
    @SerializedName("deathsPerOneMillion") @Expose
    val deathsPerOneMillion: Int,
    @SerializedName("totalTests") @Expose
    val totalTests:Int,
    @SerializedName("testsPerOneMillion") @Expose
    val testsPerOneMillion:Int
)
