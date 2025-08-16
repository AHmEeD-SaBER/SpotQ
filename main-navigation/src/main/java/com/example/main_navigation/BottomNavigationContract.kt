package com.example.main_navigation

import com.example.core_ui.base.UiEffect
import com.example.core_ui.base.UiEvent
import com.example.core_ui.base.UiState
import com.example.main_navigation.models.BottomNavItem

class BottomNavigationContract {
    data class State(
        val items: List<BottomNavItem> = emptyList()
    ): UiState

    sealed class Event : UiEvent {
        data class OnTabSelected(val route: Any) : Event()
    }

    sealed class Effect : UiEffect
}