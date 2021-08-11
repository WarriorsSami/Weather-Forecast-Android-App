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

        currentWeather.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer

            val groupLoading = activity?.findViewById<Group>(R.id.group_loading)
            groupLoading?.visibility = View.GONE

            updateLocation("Calafat")
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
            updateWeatherDescription(it.weatherDescriptions)

            val imageView = activity?.findViewById<ImageView>(R.id.imageView_condition_icon)
            if (imageView != null) {
                GlideApp.with(this@CurrentWeatherFragment)
                    .load(it.weatherIcons.first())
                    .into(imageView)
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

        val temperatureView = activity?.findViewById<TextView>(R.id.textView_temperature)
        temperatureView?.text = "$temperature$unitAbbreviation"

        val feelsLikeView = activity?.findViewById<TextView>(R.id.textView_feels_like_temperature)
        feelsLikeView?.text = "Feels like: $feelsLike$unitAbbreviation"
    }

    private fun updatePrecipitation(precipitationVolume: Double) {
        val unitAbreviation = chooseUnitAbbreviation("mm", "in")

        val precipitationView = activity?.findViewById<TextView>(R.id.textView_precipitation)
        precipitationView?.text = "Precipitation: $precipitationVolume $unitAbreviation"
    }

    private fun updateCloudCover(cloudCoverPercentage: Double) {
        val cloudCoverView = activity?.findViewById<TextView>(R.id.textView_cloud_cover)
        cloudCoverView?.text = "Cloud Cover: $cloudCoverPercentage%"
    }

    private fun updateHumidity(humidityPercentage: Double) {
        val humidityView = activity?.findViewById<TextView>(R.id.textView_humidity)
        humidityView?.text = "Humidity: $humidityPercentage%"
    }

    private fun updatePressure(pressure: Double) {
        val unitAbbreviation = chooseUnitAbbreviation("mmHg", "PSI")

        val pressureView = activity?.findViewById<TextView>(R.id.textView_pressure)
        pressureView?.text = "Pressure: $pressure $unitAbbreviation"
    }

    private fun updateUVIndex(uvIndex: Double) {
        val uvIndexView = activity?.findViewById<TextView>(R.id.textView_uv_index)
        uvIndexView?.text = "UV Index: $uvIndex"
    }

    private fun updateVisibility(visibility: Double) {
        val unitAbbreviation = chooseUnitAbbreviation("km", "miles")

        val visibilityView = activity?.findViewById<TextView>(R.id.textView_visibility)
        visibilityView?.text = "Visibility: $visibility $unitAbbreviation"
    }

    private fun updateWind(windDirection: String, windSpeed: Double) {
        val unitAbbreviation = chooseUnitAbbreviation("km/h", "mph")

        val windView = activity?.findViewById<TextView>(R.id.textView_wind)
        windView?.text = "Wind: $windDirection, $windSpeed $unitAbbreviation"
    }

    private fun updateObservationTime(time: String) {
        val observationTimeView = activity?.findViewById<TextView>(R.id.textView_observation_time)
        observationTimeView?.text = "Observation TIme: $time"
    }

    private fun updateWeatherDescription(description: List<String>) {
        val weatherDescriptions = description.joinToString(prefix = "", postfix = "", separator = ", ")

        val weatherDescriptionView = activity?.findViewById<TextView>(R.id.textView_condition)
        weatherDescriptionView?.text = "$weatherDescriptions"
    }
}