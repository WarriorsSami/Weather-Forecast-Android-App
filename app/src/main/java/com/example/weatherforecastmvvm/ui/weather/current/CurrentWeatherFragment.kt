package com.example.weatherforecastmvvm.ui.weather.current

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weatherforecastmvvm.R
import com.example.weatherforecastmvvm.internal.glide.GlideApp
import com.example.weatherforecastmvvm.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import org.w3c.dom.Text

class CurrentWeatherFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: CurrentWeatherViewModelFactory by instance()

    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    @DelicateCoroutinesApi
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(CurrentWeatherViewModel::class.java)

        bindUI()
    }

    @DelicateCoroutinesApi
    private fun bindUI() = launch {
        val currentWeather = viewModel.weather.await()
        val weatherLocation = viewModel.weatherLocation.await()

        weatherLocation.observe(viewLifecycleOwner, Observer { location ->
            if (location == null) return@Observer

            updateLocation(location.name)
        })

        currentWeather.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer

            group_loading?.visibility = View.GONE

            updateDateToToday()
            updateTemperatures(it.temperature, it.feelsLike)
            updatePrecipitation(it.precipitation)
            updateCloudCover(it.cloudCover)
            updateHumidity(it.humidity)
            updatePressure(it.pressure)
            updateUVIndex(it.uvIndex)
            updateVisibility(it.visibility)
            updateWind(it.windDirection, it.windSpeed)
            updateObservationTime(it.observationTime)
            updateWeatherDescription(it.conditionText)

            if (imageView_condition_icon != null) {
                GlideApp.with(this@CurrentWeatherFragment)
                    .load("http:" + it.conditionIconUrl)
                    .into(imageView_condition_icon)
            }
        })
    }

    private fun chooseUnitAbbreviation(metric: String, imperial: String): String {
        return if(viewModel.isMetric) metric else imperial
    }

    private fun updateLocation(location: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }

    private fun updateDateToToday() {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Today"
    }

    private fun updateTemperatures(temperature: Double, feelsLike: Double) {
        val unitAbbreviation = chooseUnitAbbreviation("°C", "°F")

        textView_temperature?.text = "$temperature$unitAbbreviation"
        textView_feels_like_temperature?.text = "Feels like: $feelsLike$unitAbbreviation"
    }

    private fun updatePrecipitation(precipitationVolume: Double) {
        val unitAbbreviation = chooseUnitAbbreviation("mm", "in")

        textView_precipitation?.text = "Precipitation: $precipitationVolume $unitAbbreviation"
    }

    private fun updateCloudCover(cloudCoverPercentage: Double) {
        textView_cloud_cover?.text = "Cloud Cover: $cloudCoverPercentage%"
    }

    private fun updateHumidity(humidityPercentage: Double) {
        textView_humidity?.text = "Humidity: $humidityPercentage%"
    }

    private fun updatePressure(pressure: Double) {
        val unitAbbreviation = chooseUnitAbbreviation("mb", "in")

        textView_pressure?.text = "Pressure: $pressure $unitAbbreviation"
    }

    private fun updateUVIndex(uvIndex: Double) {
        textView_uv_index?.text = "UV Index: $uvIndex"
    }

    private fun updateVisibility(visibility: Double) {
        val unitAbbreviation = chooseUnitAbbreviation("km", "miles")

        textView_visibility?.text = "Visibility: $visibility $unitAbbreviation"
    }

    private fun updateWind(windDirection: String, windSpeed: Double) {
        val unitAbbreviation = chooseUnitAbbreviation("kph", "mph")

        textView_wind?.text = "Wind: $windDirection, $windSpeed $unitAbbreviation"
    }

    private fun updateObservationTime(time: String) {
        textView_observation_time?.text = "Observation Time: $time"
    }

    private fun updateWeatherDescription(description: String) {
        textView_condition?.text = "$description"
    }
}