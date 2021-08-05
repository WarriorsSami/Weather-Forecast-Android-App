package com.example.weatherforecastmvvm.data.db.unitlocalized

import androidx.room.ColumnInfo

data class MetricCurrentWeatherEntry (
    @ColumnInfo(name = "cloudcover")
    override val cloudCover: Double,
    @ColumnInfo(name = "feelslike")
    override val feelsLike: Double,
    @ColumnInfo(name = "humidity")
    override val humidity: Double,
    @ColumnInfo(name = "isDay")
    override val isDay: String,
    @ColumnInfo(name = "observationTime")
    override val observationTime: String,
    @ColumnInfo(name = "precip")
    override val precipitation: Double,
    @ColumnInfo(name = "pressure")
    override val pressure: Double,
    @ColumnInfo(name = "temperature")
    override val temperature: Double,
    @ColumnInfo(name = "uvIndex")
    override val uvIndex: Double,
    @ColumnInfo(name = "visibility")
    override val visibility: Double,
    @ColumnInfo(name = "weatherDescriptions")
    override val weatherDescriptions: List<String>,
    @ColumnInfo(name = "weatherIcons")
    override val weatherIcons: List<String>,
    @ColumnInfo(name = "windDegree")
    override val windDegree: Double,
    @ColumnInfo(name = "windDir")
    override val windDirection: String,
    @ColumnInfo(name = "windSpeed")
    override val windSpeed: Double

): UnitSpecificCurrentWeatherEntry