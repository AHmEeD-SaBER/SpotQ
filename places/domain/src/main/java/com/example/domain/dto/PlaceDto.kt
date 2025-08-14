package com.example.domain.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


@Parcelize
@Serializable
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
    val categories: @Serializable List<String> = emptyList(),
    val hasImage: Boolean = false,
    val hasDescription: Boolean = false,
    val formattedDistance: String = "",
    val mainCategory: String = ""
) : Parcelable