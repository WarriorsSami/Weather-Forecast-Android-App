package com.example.weatherforecastmvvm.data.repository

import androidx.lifecycle.LiveData
import com.example.weatherforecastmvvm.data.db.CurrentWeatherDAO
import com.example.weatherforecastmvvm.data.db.unitlocalized.UnitSpecificCurrentWeatherEntry
import com.example.weatherforecastmvvm.data.network.WeatherNetworkDataSource
import com.example.weatherforecastmvvm.data.network.response.CurrentWeatherResponse
import kotlinx.coroutines.*
import org.kodein.di.bindings.WithContext
import org.threeten.bp.ZonedDateTime
import java.util.*

class ForecastRepositoryImpl(
    private val currentWeatherDAO: CurrentWeatherDAO,
    private val weatherNetworkDataSource: WeatherNetworkDataSource
) : ForecastRepository {

    init {
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever { newCurrentWeather ->
            persistFetchedCurrentWeather(newCurrentWeather)
        }
    }

    override suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry> {
        return withContext(Dispatchers.IO) {
            initWeatherData()
            return@withContext currentWeatherDAO.getWeatherMetric()
        }
    }

    @DelicateCoroutinesApi
    private fun persistFetchedCurrentWeather(fetcheCurrentWeather: CurrentWeatherResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDAO.upsert(fetcheCurrentWeather.currentWeatherEntry)
        }
    }

    private suspend fun initWeatherData() {
        if (isFetchCurrentNeeded(ZonedDateTime.now().minusHours(1)))
            fetchCurrentWeather()
    }

    private suspend fun fetchCurrentWeather() {
        weatherNetworkDataSource.fetchCurrentWeather(
            "Calafat",
            Locale.getDefault().language
        )
    }

    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }
}