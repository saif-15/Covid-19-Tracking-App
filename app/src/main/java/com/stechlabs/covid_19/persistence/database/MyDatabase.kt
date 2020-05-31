package com.stechlabs.covid_19.persistence.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.stechlabs.covid_19.models.persistence.Country
import com.stechlabs.covid_19.persistence.Dao.CountryDao
import com.stechlabs.covid_19.utils.DateTimeConverter

@Database(entities = [Country::class], version = 1)
@TypeConverters(DateTimeConverter::class)
abstract class MyDatabase : RoomDatabase() {


    abstract fun countryDao(): CountryDao

    companion object {
        val DATABASE_NAME = "com.stechlabs.covid_19.persistence.database.DATABASE"
        private var instance: MyDatabase? = null

        fun getInstance(context: Context): MyDatabase? {
            if (instance == null) {
                synchronized(MyDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MyDatabase::class.java, DATABASE_NAME
                    )
                        .fallbackToDestructiveMigration()// when version increments, it migrates (deletes db and creates new) - else it crashes
                        .build()
                }
            }
            return instance
        }
    }
}