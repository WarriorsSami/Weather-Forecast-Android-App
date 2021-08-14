package com.example.weatherforecastmvvm.ui.weather.current

import androidx.lifecycle.ViewModel
import com.example.weatherforecastmvvm.data.provider.UnitSystemProvider
import com.example.weatherforecastmvvm.data.repository.ForecastRepository
import com.example.weatherforecastmvvm.internal.UnitSystem
import com.example.weatherforecastmvvm.internal.lazyDeferred
import kotlinx.coroutines.DelicateCoroutinesApi

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitSystemProvider: UnitSystemProvider
): ViewModel() {
    private val unitSystem = unitSystemProvider.getUnitSystem()

    val isMetric: Boolean
        get() = unitSystem == UnitSystem.METRIC

    @DelicateCoroutinesApi
    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather(isMetric)
    }

    @DelicateCoroutinesApi
    val weatherLocation by lazyDeferred {
        forecastRepository.getWeatherLocation()
    }
}