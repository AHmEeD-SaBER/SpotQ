package com.example.data.utils

import com.example.core_data.database.PlaceEntity
import com.example.core_data.network.models.places.Place
import com.example.core_data.network.models.place_details.PlaceDetailsResponse
import com.example.core_data.network.models.place_details.Address
import com.example.domain.dto.PlaceDto

object PlaceMapper {

    fun entityToDto(entity: PlaceEntity): PlaceDto = PlaceDto(
        xid = entity.xid,
        name = entity.name,
        latitude = entity.latitude,
        longitude = entity.longitude,
        rate = entity.rate,
        kinds = entity.kinds,

        imageUrl = entity.imageUrl,
        description = entity.description,
        fullAddress = entity.fullAddress,
        shortAddress = entity.shortAddress,
        websiteUrl = entity.websiteUrl,

        distance = entity.distance,
        categories = entity.categories,
        hasImage = entity.hasImage,
        hasDescription = entity.hasDescription,
        formattedDistance = entity.formattedDistance,
        mainCategory = entity.mainCategory
    )

    fun dtoToEntity(dto: PlaceDto): PlaceEntity = PlaceEntity(
        xid = dto.xid,
        name = dto.name,
        latitude = dto.latitude,
        longitude = dto.longitude,
        rate = dto.rate,
        kinds = dto.kinds,

        imageUrl = dto.imageUrl,
        description = dto.description,
        fullAddress = dto.fullAddress,
        shortAddress = dto.shortAddress,
        websiteUrl = dto.websiteUrl,

        distance = dto.distance,
        categories = dto.categories,
        hasImage = dto.hasImage,
        hasDescription = dto.hasDescription,
        formattedDistance = dto.formattedDistance,
        mainCategory = dto.mainCategory
    )

    fun mapToPlaceDto(
        place: Place,
        placeDetails: PlaceDetailsResponse?,
        userLatitude: Double,
        userLongitude: Double
    ): PlaceDto {
        val placeLat = place.point?.lat ?: 0.0
        val placeLon = place.point?.lon ?: 0.0

        val distance = DistanceCalculator.calculateDistance(
            userLat = userLatitude,
            userLon = userLongitude,
            placeLat = placeLat,
            placeLon = placeLon
        )
        val categories = place.kinds
            ?.split(",")
            ?.map { it.trim() }
            ?: emptyList()

        val firstKind = categories.firstOrNull() ?: "Unknown"


        return PlaceDto(
            xid = place.xid ?: "",
            name = place.name ?: "Unknown",
            latitude = placeLat,
            longitude = placeLon,
            rate = place.rate ?: 0,
            kinds = firstKind,

            imageUrl = placeDetails?.preview?.source?.takeIf { isDirectImageUrl(it) },

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

    private fun isDirectImageUrl(url: String?): Boolean {
        if (url.isNullOrBlank()) return false
        val lower = url.lowercase()
        return (lower.contains("upload.wikimedia.org") ||
                lower.endsWith(".jpg") || lower.endsWith(".jpeg") || lower.endsWith(".png"))
    }

}