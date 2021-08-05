package com.example.weatherforecastmvvm.data.db.unitlocalized

import androidx.room.ColumnInfo

data class MetricCurrentWeatherEntry (
    @ColumnInfo(name = "cloudcover")
    override val cloudCover: Double,
    @ColumnInfo(name = "feelslike")
    override val feelsLike: Double,
    @ColumnInfo(name = "")
    override val humidity: Double,
    override val isDay: String,
    override val observationTime: String,
    override val precipitation: Double,
    override val pressure: Double,
    override val temperature: Double,
    override val uvIndex: Double,
    override val visibility: Double,
    override val weatherCode: Double,
    override val weatherDescriptions: List<String>,
    override val weatherIcons: List<String>,
    override val windDegree: Double,
    override val windDirection: String,
    override val windSpeed: Double

): UnitSpecificCurrentWeatherEntry