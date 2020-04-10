package com.stechlabs.covid_19.models.apiResponse

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Global(

        @SerializedName("cases")
        @Expose
        val cases:Int,

        @SerializedName("deaths")
        @Expose
        val deaths:Int,

        @SerializedName("recovered")
        @Expose
        val recovered:Int
)