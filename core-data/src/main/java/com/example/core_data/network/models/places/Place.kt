package com.example.core_data.network.models.places


import com.google.gson.annotations.SerializedName

data class Place(
    @SerializedName("dist")
    val dist: Double?,
    @SerializedName("kinds")
    val kinds: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("osm")
    val osm: String?,
    @SerializedName("point")
    val point: Point?,
    @SerializedName("rate")
    val rate: Int?,
    @SerializedName("wikidata")
    val wikidata: String?,
    @SerializedName("xid")
    val xid: String?
)