package com.example.core_data.network.models.place_details


import com.google.gson.annotations.SerializedName

data class Point(
    @SerializedName("lat")
    val lat: Double?,
    @SerializedName("lon")
    val lon: Double?
)