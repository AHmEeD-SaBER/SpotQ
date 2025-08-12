package com.example.core_data.network.models.place_details


import com.google.gson.annotations.SerializedName

data class PlaceDetailsResponse(
    @SerializedName("address")
    val address: Address?,
    @SerializedName("bbox")
    val bbox: Bbox?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("kinds")
    val kinds: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("osm")
    val osm: String?,
    @SerializedName("otm")
    val otm: String?,
    @SerializedName("point")
    val point: Point?,
    @SerializedName("preview")
    val preview: Preview?,
    @SerializedName("rate")
    val rate: String?,
    @SerializedName("sources")
    val sources: Sources?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("voyage")
    val voyage: String?,
    @SerializedName("wikidata")
    val wikidata: String?,
    @SerializedName("wikipedia")
    val wikipedia: String?,
    @SerializedName("wikipedia_extracts")
    val wikipediaExtracts: WikipediaExtracts?,
    @SerializedName("xid")
    val xid: String?
)