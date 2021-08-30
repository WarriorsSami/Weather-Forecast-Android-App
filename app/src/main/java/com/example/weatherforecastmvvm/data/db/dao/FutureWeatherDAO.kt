package com.example.weatherforecastmvvm.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherforecastmvvm.data.db.entity.FutureWeatherEntry
import com.example.weatherforecastmvvm.data.db.unitlocalized.future.list.ImperialSimpleFutureWeatherEntry
import com.example.weatherforecastmvvm.data.db.unitlocalized.future.list.MetricSimpleFutureWeatherEntry
import org.threeten.bp.LocalDate

@Dao
interface FutureWeatherDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(futureWeatherEntries: List<FutureWeatherEntry>)

    @Query("SELECT * FROM future_weather WHERE date(date) >= date(:startDate)")
    fun getWeatherMetric(startDate: LocalDate): LiveData<List<MetricSimpleFutureWeatherEntry>>

    @Query("SELECT * FROM future_weather WHERE date(date) >= date(:startDate)")
    fun getWeatherImperial(startDate: LocalDate): LiveData<List<ImperialSimpleFutureWeatherEntry>>

    @Query("SELECT count(id) FROM future_weather WHERE date(date) >= date(:startDate)")
    fun countFutureWeather(startDate: LocalDate): Int

    @Query("DELETE FROM future_weather WHERE date(date) < date(:firstDateToKeep)")
    fun deleteOldEntries(firstDateToKeep: LocalDate)
}