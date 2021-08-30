package com.example.weatherforecastmvvm.ui.weather.future.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherforecastmvvm.data.provider.UnitSystemProvider
import com.example.weatherforecastmvvm.data.repository.ForecastRepository
import com.example.weatherforecastmvvm.ui.weather.future.list.FutureListWeatherViewModel
import org.threeten.bp.LocalDate

class FutureDetailWeatherViewModelFactory(
        private val detailDate: LocalDate,
        private val forecastRepository: ForecastRepository,
        private val unitSystemProvider: UnitSystemProvider
): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T: ViewModel?> create(modelClass: Class<T>): T {
        return FutureDetailWeatherViewModel(detailDate, forecastRepository, unitSystemProvider) as T
    }
}