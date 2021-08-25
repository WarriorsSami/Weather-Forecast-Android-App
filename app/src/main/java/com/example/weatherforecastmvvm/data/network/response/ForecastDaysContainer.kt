package com.example.weatherforecastmvvm.data.network.response

import com.example.weatherforecastmvvm.data.db.entity.FutureWeatherEntry
import com.google.gson.annotations.SerializedName


data class ForecastDaysContainer(
    @SerializedName("forecastday")
    val dayEntries: List<FutureWeatherEntry>
)