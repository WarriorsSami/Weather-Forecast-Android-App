package com.example.weatherforecastmvvm.ui.weather.current

import androidx.lifecycle.ViewModel
import com.example.weatherforecastmvvm.data.provider.UnitSystemProvider
import com.example.weatherforecastmvvm.data.repository.ForecastRepository
import com.example.weatherforecastmvvm.internal.UnitSystem
import com.example.weatherforecastmvvm.internal.lazyDeferred
import com.example.weatherforecastmvvm.ui.base.WeatherViewModel
import kotlinx.coroutines.DelicateCoroutinesApi

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitSystemProvider: UnitSystemProvider
): WeatherViewModel(forecastRepository, unitSystemProvider) {

    @DelicateCoroutinesApi
    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather(super.isMetric)
    }
}