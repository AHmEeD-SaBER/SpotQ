package com.example.core_data.network.models.place_details


import com.google.gson.annotations.SerializedName

data class WikipediaExtracts(
    @SerializedName("html")
    val html: String?,
    @SerializedName("text")
    val text: String?,
    @SerializedName("title")
    val title: String?
)