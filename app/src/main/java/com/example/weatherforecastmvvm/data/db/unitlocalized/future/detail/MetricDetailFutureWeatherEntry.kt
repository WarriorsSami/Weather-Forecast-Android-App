package com.example.weatherforecastmvvm.data.db.unitlocalized.future.detail

import androidx.room.ColumnInfo
import org.threeten.bp.LocalDate

class MetricDetailFutureWeatherEntry(
        @ColumnInfo(name = "date")
        override val date: LocalDate,
        @ColumnInfo(name = "condition_text")
        override val conditionText: String,
        @ColumnInfo(name = "condition_icon")
        override val conditionIconUrl: String,
        @ColumnInfo(name = "avgtempC")
        override val avgTemperature: Double,
        @ColumnInfo(name = "maxtempC")
        override val maxTemperature: Double,
        @ColumnInfo(name = "mintempC")
        override val minTemperature: Double,
        @ColumnInfo(name = "avghumidity")
        override val avgHumidity: Double,
        @ColumnInfo(name = "avgvisKm")
        override val avgVisibility: Double,
        @ColumnInfo(name = "dailyChanceOfRain")
        override val dailyChanceOfRain: Int,
        @ColumnInfo(name = "dailyChangeOfSnow")
        override val dailyChanceOfSnow: Int,
        @ColumnInfo(name = "maxwindKph")
        override val maxWindSpeed: Double,
        @ColumnInfo(name = "totalprecipMm")
        override val totalPrecipitation: Double,
        @ColumnInfo(name = "uv")
        override val uvIndex: Double

): UnitSpecificDetailFutureWeatherEntry {
}
