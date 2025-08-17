package com.example.ui

import com.example.core_domain.dto.PlaceDto
import com.example.core_ui.base.UiEffect
import com.example.core_ui.base.UiEvent
import com.example.core_ui.base.UiState

class FavoritesContract {

    data class State(
        val isLoading: Boolean = false,
        val error: Int? = null,
        val subtitleError: Int? = null,
        val favorites: List<PlaceDto> = emptyList()
    ) : UiState

    sealed class Event : UiEvent {
        data class NavigateToDetails(val place: PlaceDto, val userId: Int) : Event()
        data class LoadFavorites(val userId: Int) : Event()
    }

    sealed class Effect : UiEffect {
        data class NavigateToDetails(val place: PlaceDto, val userId: Int) : Effect()
        data class ShowError(val message: Int) : Effect()
        data class ShowMessage(val message: Int) : Effect()
    }
}