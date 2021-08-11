package com.example.weatherforecastmvvm.ui.weather.current

import androidx.lifecycle.ViewModel
import com.example.weatherforecastmvvm.data.repository.ForecastRepository
import com.example.weatherforecastmvvm.internal.UnitSystem
import com.example.weatherforecastmvvm.internal.lazyDeferred
import kotlinx.coroutines.DelicateCoroutinesApi

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository
): ViewModel() {
    private val unitSystem = UnitSystem.METRIC

    val isMetric: Boolean
        get() = unitSystem == UnitSystem.METRIC

    @DelicateCoroutinesApi
    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather(isMetric)
    }
}