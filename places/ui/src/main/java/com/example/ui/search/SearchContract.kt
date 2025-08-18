package com.example.ui.search

import com.example.core_ui.base.UiEffect
import com.example.core_ui.base.UiEvent
import com.example.core_ui.base.UiState
import com.example.core_domain.dto.PlaceDto

class SearchContract {
    data class State(
        val isLoading: Boolean = false,
        val error: Int? = null,
        val searchQuery: String = "",
        val allPlaces: List<PlaceDto> = emptyList(), // Original list from API
        val filteredPlaces: List<PlaceDto> = emptyList(), // Filtered results
        val locationName: String? = null
    ) : UiState

    sealed class Events : UiEvent {
        data class SearchQueryChanged(val query: String) : Events()
        data class PlaceClicked(val place: PlaceDto) : Events()
        data object ClearSearch : Events()
        data object LoadPlaces : Events()
        data object Retry : Events()
        data object NavigateBack : Events()
    }

    sealed class Effects : UiEffect {
        data class NavigateToPlaceDetails(val place: PlaceDto) : Effects()
        data class ShowError(val title: Int, val subtitle: Int) : Effects()
        data object NavigateBack : Effects()
    }
}