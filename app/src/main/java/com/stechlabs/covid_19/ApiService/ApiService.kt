package com.stechlabs.covid_19.ApiService

import com.stechlabs.covid_19.models.apiResponse.Country
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("countries")
    suspend fun getAllResults(): Response<List<Country>>

}