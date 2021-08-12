package com.example.weatherforecastmvvm.data.db

import android.content.Context
import androidx.room.*
import com.example.weatherforecastmvvm.data.db.entity.CurrentWeatherEntry


@Database (
    entities = [CurrentWeatherEntry::class],
    version = 4
)
@TypeConverters(Converters::class)
abstract class ForecastDatabase: RoomDatabase() {
    abstract fun currentWeatherDAO(): CurrentWeatherDAO

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