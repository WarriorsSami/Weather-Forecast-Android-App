package com.example.weatherforecastmvvm.data.db.unitlocalized

import com.google.gson.annotations.SerializedName

interface UnitSpecificCurrentWeatherEntry {
    val cloudCover: Double
    val feelsLike: Double
    val humidity: Double
    val isDay: String
    val observationTime: String
    val precipitation: Double
    val pressure: Double
    val temperature: Double
    val uvIndex: Double
    val visibility: Double
    val windDirection: String
    val windSpeed: Double
    val conditionText: String
    val conditionIconUrl: String
    val airQuality: Double
}