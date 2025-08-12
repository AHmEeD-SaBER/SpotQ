package com.example.core_data.network.models.place_details


import com.google.gson.annotations.SerializedName

data class Sources(
    @SerializedName("attributes")
    val attributes: List<String?>?,
    @SerializedName("geometry")
    val geometry: String?
)