package com.example.weatherforecastmvvm.data.repository

import androidx.lifecycle.LiveData
import com.example.weatherforecastmvvm.data.db.entity.WeatherLocation
import com.example.weatherforecastmvvm.data.db.unitlocalized.current.UnitSpecificCurrentWeatherEntry

interface ForecastRepository {
    suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry>

    suspend fun getWeatherLocation(): LiveData<WeatherLocation>
}