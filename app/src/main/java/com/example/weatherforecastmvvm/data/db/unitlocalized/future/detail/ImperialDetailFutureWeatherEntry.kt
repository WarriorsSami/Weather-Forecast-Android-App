package com.example.weatherforecastmvvm.data.db.unitlocalized.future.detail

import androidx.room.ColumnInfo
import org.threeten.bp.LocalDate

class ImperialDetailFutureWeatherEntry(
        @ColumnInfo(name = "date")
        override val date: LocalDate,
        @ColumnInfo(name = "condition_text")
        override val conditionText: String,
        @ColumnInfo(name = "condition_icon")
        override val conditionIconUrl: String,
        @ColumnInfo(name = "avgtempF")
        override val avgTemperature: Double,
        @ColumnInfo(name = "maxtempF")
        override val maxTemperature: Double,
        @ColumnInfo(name = "mintempF")
        override val minTemperature: Double,
        @ColumnInfo(name = "avghumidity")
        override val avgHumidity: Double,
        @ColumnInfo(name = "avgvisMiles")
        override val avgVisibility: Double,
        @ColumnInfo(name = "dailyChanceOfRain")
        override val dailyChanceOfRain: Int,
        @ColumnInfo(name = "dailyChanceOfSnow")
        override val dailyChanceOfSnow: Int,
        @ColumnInfo(name = "maxwindMph")
        override val maxWindSpeed: Double,
        @ColumnInfo(name = "totalprecipIn")
        override val totalPrecipitation: Double,
        @ColumnInfo(name = "uv")
        override val uvIndex: Double

): UnitSpecificDetailFutureWeatherEntry {
}
