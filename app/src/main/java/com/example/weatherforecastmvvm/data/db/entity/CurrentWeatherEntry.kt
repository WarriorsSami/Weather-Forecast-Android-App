package com.example.weatherforecastmvvm.data.db.entity


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


const val CURRENT_WEATHER_ID = 0

@Entity(tableName = "current_weather")
data class CurrentWeatherEntry(
    @SerializedName("cloud")
    val cloudcover: Double,
    @SerializedName("feelslike_c")
    val feelslike: Double,
    @SerializedName("feelslike_f")
    val feelslikeImperial: Double,
    val humidity: Double,
    @SerializedName("is_day")
    val isDay: String,
    @SerializedName("last_updated")
    val observationTime: String,
    @SerializedName("precip_mm")
    val precip: Double,
    @SerializedName("precip_in")
    val precipImperial: Double,
    @SerializedName("pressure_mb")
    val pressure: Double,
    @SerializedName("pressure_in")
    val pressureImperial: Double,
    @SerializedName("temp_c")
    val temperature: Double,
    @SerializedName("temp_f")
    val temperatureImperial: Double,
    @SerializedName("uv")
    val uvIndex: Double,
    @SerializedName("vis_km")
    val visibility: Double,
    @SerializedName("vis_miles")
    val visibilityImperial: Double,
    @Embedded(prefix = "condition_")
    val condition: Condition,
    @SerializedName("wind_dir")
    val windDir: String,
    @SerializedName("wind_kph")
    val windSpeed: Double,
    @SerializedName("wind_mph")
    val windSpeedImperial: Double,
    @Embedded(prefix = "aqi_")
    val airQuality: AQI
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = CURRENT_WEATHER_ID
}