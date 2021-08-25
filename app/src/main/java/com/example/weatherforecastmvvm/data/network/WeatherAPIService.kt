package com.example.weatherforecastmvvm.data.network

import com.example.weatherforecastmvvm.data.network.ConnectivityInterceptor
import com.example.weatherforecastmvvm.data.network.response.CurrentWeatherResponse
import com.example.weatherforecastmvvm.data.network.response.FutureWeatherResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val API_KEY = "e29e3a1332bf4d95a6a104221211807"
const val BASE_URL = "http://api.weatherapi.com/v1/"


interface WeatherAPIService {

    // Request URI - current weather
    // http://api.weatherapi.com/v1/current.json?key=e29e3a1332bf4d95a6a104221211807&q=Calafat&lang=en

    @GET("current.json")
    fun getCurrentWeatherAsync(
        @Query("q") location: String,
        @Query("lang") languageCode: String = "en"
    ): Deferred<CurrentWeatherResponse>

    // Request URI - future weather
    // http://api.weatherapi.com/v1/forecast.json?key=e29e3a1332bf4d95a6a104221211807&q=Calafat&days=3&lang=en

    @GET("forecast.json")
    fun getFutureWeatherAsync(
        @Query("q") location: String,
        @Query("days") daysNumber: Int,
        @Query("lang") languageCode: String = "en"
    ): Deferred<FutureWeatherResponse>

    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): WeatherAPIService {
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("key", API_KEY)
                    .build()

                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherAPIService::class.java)
        }
    }
}