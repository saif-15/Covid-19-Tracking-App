package com.stechlabs.covid_19.Persistence.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.stechlabs.covid_19.models.persistence.Global

@Dao
interface GlobalDao {

    @Insert
    suspend fun caheGLobalData(global:Global)

    @Query("SELECT * FROM global LIMIT 1")
    suspend fun getGlobalResults():Global
}