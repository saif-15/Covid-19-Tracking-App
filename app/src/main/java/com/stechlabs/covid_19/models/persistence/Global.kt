package com.stechlabs.covid_19.models.persistence

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "global")
data class Global(

    @PrimaryKey
    val id:Int,
    val cases:Int,
    val deaths:Int,
    val recovered:Int
)