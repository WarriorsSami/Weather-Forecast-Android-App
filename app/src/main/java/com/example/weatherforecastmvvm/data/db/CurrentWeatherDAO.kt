package com.example.weatherforecastmvvm.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherforecastmvvm.data.db.entity.CURRENT_WEATHER_ID
import com.example.weatherforecastmvvm.data.db.entity.CurrentWeatherEntry
import com.example.weatherforecastmvvm.data.db.unitlocalized.ImperialCurrentWeatherEntry
import com.example.weatherforecastmvvm.data.db.unitlocalized.MetricCurrentWeatherEntry


@Dao
interface CurrentWeatherDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherEntry: CurrentWeatherEntry)

    @Query("SELECT * FROM current_weather WHERE id = $CURRENT_WEATHER_ID")
    fun getWeatherMetric(): LiveData<MetricCurrentWeatherEntry>

    @Query("SELECT * FROM current_weather WHERE id = $CURRENT_WEATHER_ID")
    fun getWeatherImperial(): LiveData<ImperialCurrentWeatherEntry>

    @Query("UPDATE current_weather SET feelslikeImperial = :feelsLike, precipImperial = :precipitation, pressureImperial = :pressure, temperatureImperial = :temperature, visibilityImperial = :visibility, windSpeedImperial = :windSpeed WHERE id = $CURRENT_WEATHER_ID")
    fun updateWeatherImperial(
        feelsLike: Double,
        precipitation: Double,
        pressure: Double,
        temperature: Double,
        visibility: Double,
        windSpeed: Double
    )
}