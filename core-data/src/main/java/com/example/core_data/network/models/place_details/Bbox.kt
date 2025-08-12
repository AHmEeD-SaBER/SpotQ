package com.example.core_data.network.models.place_details


import com.google.gson.annotations.SerializedName

data class Bbox(
    @SerializedName("lat_max")
    val latMax: Double?,
    @SerializedName("lat_min")
    val latMin: Double?,
    @SerializedName("lon_max")
    val lonMax: Double?,
    @SerializedName("lon_min")
    val lonMin: Double?
)