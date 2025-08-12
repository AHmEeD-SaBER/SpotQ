package com.example.ui

import com.example.core_ui.base.UiEffect
import com.example.core_ui.base.UiEvent
import com.example.core_ui.base.UiState
import com.example.domain.dto.PlaceDto

class PlacesContract {
    data class State(
        val isLoading: Boolean = false,
        val isLoadingLocation: Boolean = false,
        val error: Int? = null,
        val places: List<PlaceDto> = emptyList(),
        val userLocation: Pair<Double, Double>? = null // (lat, lon)
    ) : UiState

    sealed class Events : UiEvent {
        data object LoadPlaces : Events() // Simplified - gets location first then loads places
        data class LoadPlacesWithCategory(val kinds: String?) : Events()
        data class LoadPlacesWithParams(
            val kinds: String? = null,
            val radius: Int? = null,
            val limit: Int? = null
        ) : Events()
        data class PlaceClicked(val place: PlaceDto) : Events() // Use xid instead of Int
        data object Retry : Events()
        data object RequestLocationPermission : Events()
    }

    sealed class Effects : UiEffect {
        data class NavigateToPlaceDetails(val place: PlaceDto) : Effects()
        data object RequestLocationPermission : Effects()
        data class ShowError(val title: Int, val subtitle: Int) : Effects()
    }
}