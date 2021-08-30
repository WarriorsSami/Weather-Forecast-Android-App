package com.example.weatherforecastmvvm.data.db.unitlocalized.future.detail

import org.threeten.bp.LocalDate

interface UnitSpecificDetailFutureWeatherEntry {
    val date: LocalDate
    val conditionText: String
    val conditionIconUrl: String
    val avgTemperature: Double
    val maxTemperature: Double
    val minTemperature: Double
    val avgHumidity: Double
    val avgVisibility: Double
    val dailyChanceOfRain: Int
    val dailyChanceOfSnow: Int
    val maxWindSpeed: Double
    val totalPrecipitation: Double
    val uvIndex: Double
}