package com.stechlabs.covid_19.Persistence.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.stechlabs.covid_19.Persistence.Dao.CountryDao
import com.stechlabs.covid_19.Persistence.Dao.GlobalDao
import com.stechlabs.covid_19.models.persistence.Country
import com.stechlabs.covid_19.models.persistence.Global

@Database(entities = [Country::class,Global::class],version = 1)
abstract class MyDatabase :RoomDatabase() {

    abstract fun _countryDao(): CountryDao
    abstract fun _globalDao(): GlobalDao

    companion object {
        private var instance: MyDatabase? = null

        fun getInstance(context: Context): MyDatabase? {
            if (instance == null) {
                synchronized(MyDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MyDatabase::class.java, "my_database"
                    )
                        .fallbackToDestructiveMigration()// when version increments, it migrates (deletes db and creates new) - else it crashes
                        .build()
                    Log.d("MainActitvty", "databse object")
                }
            }
            return instance
        }
    }
}