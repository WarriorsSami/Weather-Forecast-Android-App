package com.example.weatherforecastmvvm.ui.weather.future.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherforecastmvvm.data.provider.UnitSystemProvider
import com.example.weatherforecastmvvm.data.repository.ForecastRepository
import com.example.weatherforecastmvvm.ui.weather.current.CurrentWeatherViewModel

class FutureListWeatherViewModelFactory(
    private val forecastRepository: ForecastRepository,
    private val unitSystemProvider: UnitSystemProvider
): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T: ViewModel?> create(modelClass: Class<T>): T {
        return FutureListWeatherViewModel(forecastRepository, unitSystemProvider) as T
    }
}