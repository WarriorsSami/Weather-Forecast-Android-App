package com.example.weatherforecastmvvm

import android.app.Application
import android.content.Context
import com.example.weatherforecastmvvm.data.db.ForecastDatabase
import com.example.weatherforecastmvvm.data.network.*
import com.example.weatherforecastmvvm.data.provider.LocationProvider
import com.example.weatherforecastmvvm.data.provider.LocationProviderImpl
import com.example.weatherforecastmvvm.data.provider.UnitSystemProvider
import com.example.weatherforecastmvvm.data.provider.UnitSystemProviderImpl
import com.example.weatherforecastmvvm.data.repository.ForecastRepository
import com.example.weatherforecastmvvm.data.repository.ForecastRepositoryImpl
import com.example.weatherforecastmvvm.ui.weather.current.CurrentWeatherViewModelFactory
import com.example.weatherforecastmvvm.ui.weather.future.detail.FutureDetailWeatherViewModelFactory
import com.example.weatherforecastmvvm.ui.weather.future.list.FutureListWeatherViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.coroutines.DelicateCoroutinesApi
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.*
import org.threeten.bp.LocalDate

class ForecastApplication: Application(), KodeinAware {
    @DelicateCoroutinesApi
    override val kodein = Kodein.lazy {
        import(androidXModule(this@ForecastApplication))

        bind() from singleton { ForecastDatabase(instance()) }
        bind() from singleton { instance<ForecastDatabase>().currentWeatherDAO() }
        bind() from singleton { instance<ForecastDatabase>().weatherLocationDAO() }
        bind() from singleton { instance<ForecastDatabase>().futureWeatherDAO() }

        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { WeatherAPIService(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind<LocationProvider>() with singleton { LocationProviderImpl(instance(), instance()) }
        bind<ForecastRepository>() with singleton { ForecastRepositoryImpl(instance(), instance(), instance(), instance(), instance()) }
        bind<UnitSystemProvider>() with singleton { UnitSystemProviderImpl(instance()) }
        bind() from provider { CurrentWeatherViewModelFactory(instance(), instance()) }
        bind() from provider { FutureListWeatherViewModelFactory(instance(), instance()) }
        bind() from provider { LocationServices.getFusedLocationProviderClient(instance<Context>()) }
        bind() from factory { detailDate: LocalDate -> FutureDetailWeatherViewModelFactory(detailDate, instance(), instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}