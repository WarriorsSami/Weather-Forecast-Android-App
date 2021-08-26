package com.example.weatherforecastmvvm.ui.base

import androidx.lifecycle.ViewModel
import com.example.weatherforecastmvvm.data.provider.UnitSystemProvider
import com.example.weatherforecastmvvm.data.repository.ForecastRepository
import com.example.weatherforecastmvvm.internal.UnitSystem
import com.example.weatherforecastmvvm.internal.lazyDeferred
import kotlinx.coroutines.DelicateCoroutinesApi

abstract class WeatherViewModel(
    private val forecastRepository: ForecastRepository,
    private val unitSystemProvider: UnitSystemProvider
): ViewModel() {
    private val unitSystem = unitSystemProvider.getUnitSystem()

    val isMetric: Boolean
        get() = unitSystem == UnitSystem.METRIC

    @DelicateCoroutinesApi
    val weatherLocation by lazyDeferred {
        forecastRepository.getWeatherLocation()
    }
}