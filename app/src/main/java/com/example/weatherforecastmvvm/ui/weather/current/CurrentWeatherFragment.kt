package com.example.weatherforecastmvvm.ui.weather.current

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherforecastmvvm.R
import com.example.weatherforecastmvvm.data.network.ConnectivityInterceptorImpl
import com.example.weatherforecastmvvm.data.network.WeatherNetworkDataSourceImpl
import com.example.weatherforecastmvvm.data.network.WeatherStackAPIService
import com.example.weatherforecastmvvm.ui.base.ScopedFragment
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(CurrentWeatherViewModel::class.java)

        bindUI()
        // val weatherAPIService = WeatherStackAPIService(ConnectivityInterceptorImpl(this.requireContext()))
        // val weatherNetworkDataSource = WeatherNetworkDataSourceImpl(weatherAPIService)

        // weatherNetworkDataSource.downloadedCurrentWeather.observe(viewLifecycleOwner, Observer {
        //     val textView = activity?.findViewById<TextView>(R.id.textView)
        //     textView?.text = it.currentWeatherEntry.toString()
        // })

        // GlobalScope.launch(Dispatchers.Main) {
        //     weatherNetworkDataSource.fetchCurrentWeather("Calafat", "en")
        // }
    }

    @DelicateCoroutinesApi
    private fun bindUI() = launch {
        val currentWeather = viewModel.weather.await()
        currentWeather.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer

            val textView = activity?.findViewById<TextView>(R.id.textView)
            textView?.text = it.toString()
        })
    }
}