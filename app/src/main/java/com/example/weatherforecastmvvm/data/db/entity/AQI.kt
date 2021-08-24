package com.example.weatherforecastmvvm.data.db.entity

import com.google.gson.annotations.SerializedName

data class AQI (
    @SerializedName("us-epa-index")
    val index: Double
)