package com.example.weatherforecastmvvm.data.db.unitlocalized

import androidx.lifecycle.LiveData
import java.math.RoundingMode
import java.text.DecimalFormat

fun convertToImperial(weatherLiveData: LiveData<MetricCurrentWeatherEntry>): MetricCurrentWeatherEntry? {
    val weather = weatherLiveData.value
    if (weather != null) {
        return MetricCurrentWeatherEntry(
            weather.cloudCover,
            convertToFahrenheit(weather.feelsLike),
            weather.humidity,
            weather.isDay,
            weather.observationTime,
            convertToInches(weather.precipitation),
            convertToPSI(weather.pressure),
            convertToFahrenheit(weather.temperature),
            weather.uvIndex,
            convertToMiles(weather.visibility),
            weather.weatherDescriptions,
            weather.weatherIcons,
            weather.windDirection,
            convertToMPH(weather.windSpeed)
        )
    }
    return null
}

fun convertToFahrenheit(value: Double): Double {
    return roundOffDecimal(value * (9 / 5) + 32)
}

fun convertToInches(value: Double): Double {
    return roundOffDecimal(value / 25.4)
}

fun convertToPSI(value: Double): Double {
    return roundOffDecimal(value / 51.7149)
}

fun convertToMiles(value: Double): Double {
    return roundOffDecimal(value * 0.6214)
}

fun convertToMPH(value: Double): Double {
    return roundOffDecimal(value / 1.609344)
}

fun roundOffDecimal(number: Double): Double {
    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.CEILING
    return df.format(number).toDouble()
}