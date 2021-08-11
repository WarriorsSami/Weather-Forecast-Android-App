package com.example.weatherforecastmvvm.data.repository

import androidx.lifecycle.LiveData
import com.example.weatherforecastmvvm.data.db.CurrentWeatherDAO
import com.example.weatherforecastmvvm.data.db.unitlocalized.UnitSpecificCurrentWeatherEntry
import com.example.weatherforecastmvvm.data.db.unitlocalized.convertToImperial
import com.example.weatherforecastmvvm.data.network.WeatherNetworkDataSource
import com.example.weatherforecastmvvm.data.network.response.CurrentWeatherResponse
import kotlinx.coroutines.*
import org.threeten.bp.ZonedDateTime
import java.util.*

@DelicateCoroutinesApi
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
            // TODO Get somehow Weather Imperial Form
            /*
                if (metric) currentWeatherDAO.getWeatherMetric()
                else currentWeatherDAO.getWeatherMetric().also {
                    it.postValue(convertToImperial(it))
                }
             */
        }
    }

    @DelicateCoroutinesApi
    private fun persistFetchedCurrentWeather(fetchedCurrentWeather: CurrentWeatherResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDAO.upsert(fetchedCurrentWeather.currentWeatherEntry)
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