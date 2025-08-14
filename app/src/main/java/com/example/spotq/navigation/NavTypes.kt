package com.example.spotq.navigation


import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.example.domain.dto.PlaceDto
import kotlinx.serialization.json.Json

val PlaceDtoNavType = object : NavType<PlaceDto>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): PlaceDto? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): PlaceDto {
        return Json.decodeFromString(Uri.decode(value))
    }

    override fun serializeAsValue(value: PlaceDto): String {
        return Uri.encode(Json.encodeToString(value))
    }

    override fun put(bundle: Bundle, key: String, value: PlaceDto) {
        bundle.putParcelable(key, value)
    }
}