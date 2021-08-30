package com.example.weatherforecastmvvm.ui.weather.future.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.weatherforecastmvvm.R
import com.example.weatherforecastmvvm.data.db.converter.LocalDateConverter
import com.example.weatherforecastmvvm.internal.DateNotFoundException
import com.example.weatherforecastmvvm.internal.glide.GlideApp
import com.example.weatherforecastmvvm.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.android.synthetic.main.future_detail_weather_fragment.*
import kotlinx.android.synthetic.main.future_detail_weather_fragment.imageView_condition_icon
import kotlinx.android.synthetic.main.future_detail_weather_fragment.textView_condition
import kotlinx.android.synthetic.main.future_detail_weather_fragment.textView_humidity
import kotlinx.android.synthetic.main.future_detail_weather_fragment.textView_precipitation
import kotlinx.android.synthetic.main.future_detail_weather_fragment.textView_temperature
import kotlinx.android.synthetic.main.future_detail_weather_fragment.textView_uv_index
import kotlinx.android.synthetic.main.future_detail_weather_fragment.textView_visibility
import kotlinx.android.synthetic.main.future_detail_weather_fragment.textView_wind
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.factory
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

class FutureDetailWeatherFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactoryInstanceFactory:
            ((LocalDate) -> FutureDetailWeatherViewModelFactory) by factory()

    private lateinit var viewModel: FutureDetailWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.future_detail_weather_fragment, container, false)
    }

    @DelicateCoroutinesApi
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val safeArgs = arguments?.let { FutureDetailWeatherFragmentArgs.fromBundle(it) }
        val date = LocalDateConverter.stringToDate(safeArgs?.dateString) ?: throw DateNotFoundException()

        viewModel = ViewModelProvider(this, viewModelFactoryInstanceFactory(date))
                .get(FutureDetailWeatherViewModel::class.java)

        bindUI()
    }

    @DelicateCoroutinesApi
    private fun bindUI() = launch(Dispatchers.Main) {
        val futureWeather = viewModel.weather.await()
        val weatherLcation = viewModel.weatherLocation.await()

        weatherLcation.observe(viewLifecycleOwner, Observer { location ->
            if (location == null) return@Observer

            updateLocation(location.name)
        })

        futureWeather.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer

            updateDate(it.date)
            updateTemperatures(it.avgTemperature, it.minTemperature, it.maxTemperature)
            updateMaxWindSpeed(it.maxWindSpeed)
            updateTotalPrecipitation(it.totalPrecipitation)
            updateChances(it.dailyChanceOfRain, it.dailyChanceOfSnow)
            updateHumidity(it.avgHumidity)
            updateVisibility(it.avgVisibility)
            updateUVIndex(it.uvIndex)
            updateWeatherDescription(it.conditionText)

            if (imageView_condition_icon != null) {
                GlideApp.with(this@FutureDetailWeatherFragment)
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

    private fun updateDate(date: LocalDate) {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle =
                date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))
    }

    private fun updateTemperatures(
            avgTemperature: Double, minTemperature: Double, maxTemperature: Double) {
        val unitAbbreviation = chooseUnitAbbreviation("°C", "°F")

        textView_temperature?.text = "$avgTemperature$unitAbbreviation"
        textView_min_temperature?.text = "Min Temperature: $minTemperature$unitAbbreviation"
        textView_max_temperature?.text = "Max Temperature: $maxTemperature$unitAbbreviation"
    }

    private fun updateMaxWindSpeed(windSpeed: Double) {
        val unitAbbreviation = chooseUnitAbbreviation("kph", "mph")

        textView_wind?.text = "Max Wind Speed: $windSpeed $unitAbbreviation"
    }

    private fun updateTotalPrecipitation(precipitationVolume: Double) {
        val unitAbbreviation = chooseUnitAbbreviation("mm", "in")

        textView_precipitation?.text = "Total Precipitation: $precipitationVolume $unitAbbreviation"
    }

    private fun updateChances(ofRain: Int, ofSnow: Int) {
        textView_rain?.text = "Chance of Rain: $ofRain%"
        textView_snow?.text = "Chance of Snow: $ofSnow%"
    }

    private fun updateHumidity(humidityPercentage: Double) {
        textView_humidity?.text = "Humidity: $humidityPercentage%"
    }

    private fun updateUVIndex(uvIndex: Double) {
        textView_uv_index?.text = "UV Index: $uvIndex"
    }

    private fun updateVisibility(visibility: Double) {
        val unitAbbreviation = chooseUnitAbbreviation("km", "miles")

        textView_visibility?.text = "Visibility: $visibility $unitAbbreviation"
    }

    private fun updateWeatherDescription(description: String) {
        textView_condition?.text = "$description"
    }
}