package com.example.core_data.network.models.place_details


import com.google.gson.annotations.SerializedName

data class Preview(
    @SerializedName("height")
    val height: Int?,
    @SerializedName("source")
    val source: String?,
    @SerializedName("width")
    val width: Int?
)