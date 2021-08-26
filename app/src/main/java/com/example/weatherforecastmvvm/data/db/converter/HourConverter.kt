package com.example.weatherforecastmvvm.data.db.converter

import androidx.room.TypeConverter
import com.example.weatherforecastmvvm.data.db.entity.HourEntry
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


val gson = Gson()

object HourConverter {
    @TypeConverter
    @JvmStatic
    fun stringToHourList(data: String): List<HourEntry> {
        val listType = object: TypeToken<List<HourEntry>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    @JvmStatic
    fun hourListToString(data: List<HourEntry>): String {
        return gson.toJson(data)
    }
}