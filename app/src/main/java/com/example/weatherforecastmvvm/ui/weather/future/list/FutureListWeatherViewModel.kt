package com.example.weatherforecastmvvm.ui.weather.future.list

import androidx.lifecycle.ViewModel
import com.example.weatherforecastmvvm.data.provider.UnitSystemProvider
import com.example.weatherforecastmvvm.data.repository.ForecastRepository
import com.example.weatherforecastmvvm.internal.lazyDeferred
import com.example.weatherforecastmvvm.ui.base.WeatherViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import org.threeten.bp.LocalDate

class FutureListWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitSystemProvider: UnitSystemProvider
) : WeatherViewModel(forecastRepository, unitSystemProvider) {

    @DelicateCoroutinesApi
    val weatherEntries by lazyDeferred {
        forecastRepository.getFutureWeatherList(LocalDate.now(), super.isMetric)
    }
}