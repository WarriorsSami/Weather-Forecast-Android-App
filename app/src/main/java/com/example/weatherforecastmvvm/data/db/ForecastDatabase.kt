package com.example.weatherforecastmvvm.data.db

import android.content.Context
import androidx.room.*
import com.example.weatherforecastmvvm.data.db.converter.HourConverter
import com.example.weatherforecastmvvm.data.db.converter.LocalDateConverter
import com.example.weatherforecastmvvm.data.db.dao.CurrentWeatherDAO
import com.example.weatherforecastmvvm.data.db.dao.FutureWeatherDAO
import com.example.weatherforecastmvvm.data.db.dao.WeatherLocationDAO
import com.example.weatherforecastmvvm.data.db.entity.CurrentWeatherEntry
import com.example.weatherforecastmvvm.data.db.entity.FutureWeatherEntry
import com.example.weatherforecastmvvm.data.db.entity.WeatherLocation


@Database (
    entities = [CurrentWeatherEntry::class,
                WeatherLocation::class,
                FutureWeatherEntry::class],
    version = 1
)
@TypeConverters(LocalDateConverter::class, HourConverter::class)
abstract class ForecastDatabase: RoomDatabase() {
    abstract fun currentWeatherDAO(): CurrentWeatherDAO
    abstract fun futureWeatherDAO(): FutureWeatherDAO
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