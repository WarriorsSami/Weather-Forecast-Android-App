package com.example.weatherforecastmvvm.data.db.unitlocalized

import com.example.weatherforecastmvvm.data.db.entity.CurrentWeatherEntry


fun convertToImperial(weather: CurrentWeatherEntry): MetricCurrentWeatherEntry {
    return MetricCurrentWeatherEntry(
        weather.cloudcover,
        convertToFahrenheit(weather.feelslike),
        weather.humidity,
        weather.isDay,
        weather.observationTime,
        convertToInches(weather.precip),
        convertToPSI(weather.pressure),
        convertToFahrenheit(weather.temperature),
        weather.uvIndex,
        convertToMiles(weather.visibility),
        weather.weatherDescriptions,
        weather.weatherIcons,
        weather.windDir,
        convertToMPH(weather.windSpeed)
    )
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
    return Math.round(number * 10.0) / 10.0
}