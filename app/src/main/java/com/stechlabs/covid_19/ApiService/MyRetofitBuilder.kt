package com.stechlabs.covid_19.ApiService

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MyRetofitBuilder {

    const val BASE_URL="https://coronavirus-19-api.herokuapp.com/"

    var retrofitBuilder=
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    val ApiService= retrofitBuilder.create(ApiService::class.java)
}