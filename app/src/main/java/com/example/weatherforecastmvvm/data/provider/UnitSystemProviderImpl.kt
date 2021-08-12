package com.example.weatherforecastmvvm.data.provider

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.example.weatherforecastmvvm.R
import com.example.weatherforecastmvvm.internal.UnitSystem


const val UNIT_SYSTEM = "UNIT_SYSTEM"

class UnitSystemProviderImpl(
    context: Context
) : UnitSystemProvider {

    private val appContext = context.applicationContext

    private val preferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    override fun getUnitSystem(): UnitSystem {
        val selectedName = preferences.getString(UNIT_SYSTEM, UnitSystem.METRIC.name)
        return UnitSystem.valueOf(selectedName!!)
    }
}