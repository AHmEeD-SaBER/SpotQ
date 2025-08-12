package com.example.data.utils

import com.example.core_data.network.models.places.Place
import com.example.core_data.network.models.place_details.PlaceDetailsResponse
import com.example.core_data.network.models.place_details.Address
import com.example.domain.dto.PlaceDto

object PlaceMapper {

    fun mapToPlaceDto(
        place: Place,
        placeDetails: PlaceDetailsResponse?,
        userLatitude: Double,
        userLongitude: Double
    ): PlaceDto {
        val categories = place.kinds?.split(",")?.map { it.trim() } ?: emptyList()
        val placeLat = place.point?.lat ?: 0.0
        val placeLon = place.point?.lon ?: 0.0

        val distance = DistanceCalculator.calculateDistance(
            userLat = userLatitude,
            userLon = userLongitude,
            placeLat = placeLat,
            placeLon = placeLon
        )

        return PlaceDto(
            xid = place.xid ?: "",
            name = place.name ?: "Unknown",
            latitude = placeLat,
            longitude = placeLon,
            rate = place.rate ?: 0,
            kinds = place.kinds ?: "",

            imageUrl = placeDetails?.preview?.source,
            description = placeDetails?.wikipediaExtracts?.text,
            fullAddress = placeDetails?.address?.let { buildFullAddress(it) },
            shortAddress = placeDetails?.address?.let { buildShortAddress(it) },
            websiteUrl = placeDetails?.url,

            distance = distance,
            categories = categories,
            hasImage = !placeDetails?.preview?.source.isNullOrEmpty(),
            hasDescription = !placeDetails?.wikipediaExtracts?.text.isNullOrEmpty(),
            formattedDistance = DistanceCalculator.formatDistance(distance),
            mainCategory = categories.firstOrNull() ?: "Unknown"
        )
    }

    private fun buildFullAddress(address: Address): String {
        val parts = listOfNotNull(
            address.road,
            address.neighbourhood,
            address.suburb,
            address.city,
            address.state,
            address.country
        )
        return parts.joinToString(", ")
    }

    private fun buildShortAddress(address: Address): String {
        val parts = listOfNotNull(
            address.city,
            address.state,
            address.country
        )
        return parts.joinToString(", ")
    }
}