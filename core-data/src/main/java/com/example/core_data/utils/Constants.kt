package com.example.core_data.utils

object Constants {
    object Endpoints {
        const val BASE_URL = "https://api.opentripmap.com/"
        const val PLACES_RADIUS = "0.1/en/places/radius"
        const val PLACE_DETAILS = "0.1/en/places/xid/{xid}"
    }

    object QueryParams {
        const val RADIUS = "radius"
        const val LON = "lon"
        const val LAT = "lat"
        const val KINDS = "kinds"
        const val FORMAT = "format"
        const val LIMIT = "limit"
        const val API_KEY = "apikey"
    }

    object PathParams {
        const val XID = "xid"
    }

    object ApiConfig {
        const val API_KEY = "5ae2e3f221c38a28845f05b6be254ba34e94ac2508766822355eaca7"
        const val DEFAULT_FORMAT = "json"
        const val DEFAULT_RADIUS = 1000000
        const val DEFAULT_LIMIT = 50
        const val DEFAULT_LATITUDE = 0.0
        const val DEFAULT_LONGITUDE = 0.0
    }

    enum class PlaceCategory(
        val displayName: String,
        val apiValue: String
    ) {
        BEACHES("Beaches & Coast", "beaches"),
        MUSEUMS("Museums", "museums"),
        RESTAURANTS("Restaurants", "restaurants"),
        NATURE("Nature & Parks", "natural"),
        HISTORIC("Historic Sites", "historic"),
        AMUSEMENTS("Entertainment", "amusements"),
        CULTURAL("Cultural Sites", "cultural"),
        SPORT("Sports & Recreation", "sport"),
        SHOPPING("Shopping", "shops"),
        NIGHTLIFE("Nightlife", "bars,pubs"),
        RELIGIOUS("Religious Sites", "religion"),
        INTERESTING_PLACES("Popular Places", "interesting_places");
    }
}