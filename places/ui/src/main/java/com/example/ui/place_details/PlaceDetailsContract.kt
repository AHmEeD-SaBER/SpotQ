package com.example.ui.place_details

import com.example.core_ui.base.UiEvent
import com.example.core_ui.base.UiState
import com.example.domain.dto.PlaceDto

class PlaceDetailsContract {
    data class State(
        val isFavorite: Boolean = false,
    ) : UiState

    sealed class Events : UiEvent {
        data class AddToFavorites(val place: PlaceDto) : Events()
    }
}