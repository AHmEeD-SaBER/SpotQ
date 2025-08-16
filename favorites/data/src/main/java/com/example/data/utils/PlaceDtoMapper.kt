package com.example.data.utils

import com.example.core_data.database.PlaceEntity
import com.example.core_domain.dto.PlaceDto

object PlaceDtoMapper {

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


}