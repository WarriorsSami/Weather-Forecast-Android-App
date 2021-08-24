package com.example.weatherforecastmvvm.data.db

import android.content.Context
import androidx.room.*
import com.example.weatherforecastmvvm.data.db.entity.CurrentWeatherEntry
import com.example.weatherforecastmvvm.data.db.entity.WeatherLocation


@Database (
    entities = [CurrentWeatherEntry::class, WeatherLocation::class],
    version = 1
)
abstract class ForecastDatabase: RoomDatabase() {
    abstract fun currentWeatherDAO(): CurrentWeatherDAO
    abstract fun weatherLocationDAO(): WeatherLocationDAO

    // Singleton Design Pattern is used here to preserve the existence of only one DB connection
    companion object {
        @Volatile private var instance: ForecastDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                ForecastDatabase::class.java, "forecast.db")
                .fallbackToDestructiveMigration()
                .build()
    }
}