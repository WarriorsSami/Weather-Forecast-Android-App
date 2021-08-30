package com.example.weatherforecastmvvm.ui.weather.future.detail

import com.example.weatherforecastmvvm.data.provider.UnitSystemProvider
import com.example.weatherforecastmvvm.data.repository.ForecastRepository
import com.example.weatherforecastmvvm.internal.lazyDeferred
import com.example.weatherforecastmvvm.ui.base.WeatherViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import org.threeten.bp.LocalDate

class FutureDetailWeatherViewModel(
        detailDate: LocalDate,
        private val forecastRepository: ForecastRepository,
        unitSystemProvider: UnitSystemProvider
) : WeatherViewModel(forecastRepository, unitSystemProvider) {

    @DelicateCoroutinesApi
    val weather by lazyDeferred {
        forecastRepository.getFutureWeatherByDate(detailDate, super.isMetric)
    }
}