package com.example.domain.dto

data class PlaceDto(
    // Basic info
    val xid: String,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val rate: Int,
    val kinds: String,

    // From PlaceDetailsResponse
    val imageUrl: String? = null,
    val description: String? = null,
    val fullAddress: String? = null,
    val shortAddress: String? = null,
    val websiteUrl: String? = null,

    // Calculated fields
    val distance: Double = 0.0,
    val categories: List<String> = emptyList(),
    val hasImage: Boolean = false,
    val hasDescription: Boolean = false,
    val formattedDistance: String = "",
    val mainCategory: String = ""
)