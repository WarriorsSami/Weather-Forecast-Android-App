package com.example.weatherforecastmvvm.data.repository

import androidx.lifecycle.LiveData
import com.example.weatherforecastmvvm.data.db.entity.WeatherLocation
import com.example.weatherforecastmvvm.data.db.unitlocalized.current.UnitSpecificCurrentWeatherEntry
import com.example.weatherforecastmvvm.data.db.unitlocalized.future.list.UnitSpecificSimpleFutureWeatherEntry
import org.threeten.bp.LocalDate

interface ForecastRepository {
    suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry>

    suspend fun getFutureWeatherList(startDate: LocalDate, metric: Boolean): LiveData<out List<UnitSpecificSimpleFutureWeatherEntry>>

    suspend fun getWeatherLocation(): LiveData<WeatherLocation>
}