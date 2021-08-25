package com.example.weatherforecastmvvm.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherforecastmvvm.data.network.response.CurrentWeatherResponse
import com.example.weatherforecastmvvm.internal.NoInternetConnectionException
import retrofit2.HttpException

class WeatherNetworkDataSourceImpl (
    private val WeatherAPIService: WeatherAPIService
) : WeatherNetworkDataSource {

    private val _downloadedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()
    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadedCurrentWeather

    override suspend fun fetchCurrentWeather(location: String, languageCode: String) {
        try {
            val fetchedCurrentWeather = WeatherAPIService
                .getCurrentWeatherAsync(location, languageCode)
                .await()
            _downloadedCurrentWeather.postValue(fetchedCurrentWeather)
        } catch(err: NoInternetConnectionException) {
            Log.e("Connectivity", "No internet connection", err)
        } catch(err: HttpException) {
            Log.e("HTTP", "400 Bad Request", err)
        }
    }
}