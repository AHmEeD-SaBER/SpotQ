package com.example.core_data.network.models.places


import com.google.gson.annotations.SerializedName

data class Point(
    @SerializedName("lat")
    val lat: Double?,
    @SerializedName("lon")
    val lon: Double?
)