package com.example.core_data.database


import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity(tableName = "places")
data class PlaceEntity(
    @PrimaryKey
    val xid: String,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val rate: Int,
    val kinds: String,

    val imageUrl: String? = null,
    val description: String? = null,
    val fullAddress: String? = null,
    val shortAddress: String? = null,
    val websiteUrl: String? = null,

    val distance: Double = 0.0,
    val categories: List<String> = emptyList(),
    val hasImage: Boolean = false,
    val hasDescription: Boolean = false,
    val formattedDistance: String = "",
    val mainCategory: String = ""
)