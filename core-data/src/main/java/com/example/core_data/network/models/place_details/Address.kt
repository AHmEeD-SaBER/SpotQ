package com.example.core_data.network.models.place_details


import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("city")
    val city: String?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("country_code")
    val countryCode: String?,
    @SerializedName("neighbourhood")
    val neighbourhood: String?,
    @SerializedName("postcode")
    val postcode: String?,
    @SerializedName("road")
    val road: String?,
    @SerializedName("state")
    val state: String?,
    @SerializedName("suburb")
    val suburb: String?
)