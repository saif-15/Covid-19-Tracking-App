package com.stechlabs.covid_19.ApiService

import com.stechlabs.covid_19.models.apiResponse.Country
import com.stechlabs.covid_19.models.apiResponse.Global
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {


    @GET("all")
    suspend fun getGlobalResult():Response<Global>

    @GET("countries")
    suspend fun getAllResults():Response<List<Country>>

    @GET("countries/{country_name}")
    suspend fun getResultByCountry(@Path("country_name") country:String):Response<Country>
}