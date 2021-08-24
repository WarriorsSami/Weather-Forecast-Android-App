package com.example.weatherforecastmvvm.data.db.unitlocalized

import androidx.room.ColumnInfo

data class MetricCurrentWeatherEntry (
    @ColumnInfo(name = "cloudcover")
    override val cloudCover: Double,
    @ColumnInfo(name = "feelslike") // add imperial version (F)
    override val feelsLike: Double,
    @ColumnInfo(name = "humidity")
    override val humidity: Double,
    @ColumnInfo(name = "isDay")
    override val isDay: String,
    @ColumnInfo(name = "observationTime")
    override val observationTime: String,
    @ColumnInfo(name = "precip") // add imperial version (in)
    override val precipitation: Double,
    @ColumnInfo(name = "pressure") // add imperial version (PSI)
    override val pressure: Double,
    @ColumnInfo(name = "temperature") // add imperial version (F)
    override val temperature: Double,
    @ColumnInfo(name = "uvIndex")
    override val uvIndex: Double,
    @ColumnInfo(name = "visibility") // add imperial version (miles)
    override val visibility: Double,
    @ColumnInfo(name = "condition_text")
    override val conditionText: String,
    @ColumnInfo(name = "condition_icon")
    override val conditionIconUrl: String,
    @ColumnInfo(name = "windDir")
    override val windDirection: String,
    @ColumnInfo(name = "windSpeed") // add imperial version (mph)
    override val windSpeed: Double

): UnitSpecificCurrentWeatherEntry