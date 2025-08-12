package com.example.core_data.network

import com.example.core_data.network.models.place_details.PlaceDetailsResponse
import com.example.core_data.network.models.places.PlacesResponse
import retrofit2.http.GET
import com.example.core_data.utils.Constants
import retrofit2.http.Path
import retrofit2.http.Query





interface PlacesApi {
    suspend fun getPlaces(
        latitude: Double? = null,
        longitude: Double? = null,
        kinds: String? = null,
        radius: Int? = null,
        limit: Int? = null
    ): PlacesResponse {
        return getPlacesInternal(
            latitude = latitude ?: Constants.ApiConfig.DEFAULT_LATITUDE,
            longitude = longitude ?: Constants.ApiConfig.DEFAULT_LONGITUDE,
            kinds = kinds,
            radius = radius ?: Constants.ApiConfig.DEFAULT_RADIUS,
            limit = limit ?: Constants.ApiConfig.DEFAULT_LIMIT,
            format = Constants.ApiConfig.DEFAULT_FORMAT,
            apiKey = Constants.ApiConfig.API_KEY
        )
    }
    @GET(Constants.Endpoints.PLACES_RADIUS)
    suspend fun getPlacesInternal(
        @Query(Constants.QueryParams.LAT) latitude: Double = Constants.ApiConfig.DEFAULT_LATITUDE,
        @Query(Constants.QueryParams.LON) longitude: Double = Constants.ApiConfig.DEFAULT_LONGITUDE,
        @Query(Constants.QueryParams.KINDS) kinds: String? = null,
        @Query(Constants.QueryParams.RADIUS) radius: Int? = Constants.ApiConfig.DEFAULT_RADIUS,
        @Query(Constants.QueryParams.LIMIT) limit: Int? = Constants.ApiConfig.DEFAULT_LIMIT,
        @Query(Constants.QueryParams.FORMAT) format: String = Constants.ApiConfig.DEFAULT_FORMAT,
        @Query(Constants.QueryParams.API_KEY) apiKey: String = Constants.ApiConfig.API_KEY
    ): PlacesResponse

    @GET(Constants.Endpoints.PLACE_DETAILS)
    suspend fun getPlaceDetails(
        @Path(Constants.PathParams.XID) xid: String,
        @Query(Constants.QueryParams.API_KEY) apiKey: String = Constants.ApiConfig.API_KEY
    ): PlaceDetailsResponse
}
