package com.example.ui.place_details

import com.example.core_ui.base.UiEffect
import com.example.core_ui.base.UiEvent
import com.example.core_ui.base.UiState
import com.example.domain.dto.PlaceDto

class PlaceDetailsContract {
    data class State(
        val isFavorite: Boolean = false,
    ) : UiState

    sealed class Events : UiEvent {
        data class AddToFavorites(val place: PlaceDto, val userId: Int) : Events()
        data class RemoveFromFavorites(val placeId: String, val userId: Int) : Events()
        data class IsFavorite(val placeId: String, val userId: Int) : Events()
        data object NavigateUp : Events()
    }

    sealed class Effect : UiEffect {
        data object NavigateUp : Effect()
        data class ShowError(val messageRes: Int) : Effect()
        data class ShowSuccess(val messageRes: Int) : Effect()
    }
}