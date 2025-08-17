package com.example.core_ui.utils

import kotlinx.serialization.Serializable

@Serializable
sealed class Routes {
    @Serializable
    data object Splash : Routes()

    @Serializable
    data object Onboarding : Routes()

    @Serializable
    data object Login : Routes()

    @Serializable
    data object Signup : Routes()

    @Serializable
    data object Places : Routes()

    @Serializable
    data object ForgotPassword : Routes()


    @Serializable
    data class Main(
        val userId: Int
    ) : Routes()

    @Serializable
    data object PlaceDetails : Routes()


    @Serializable
    data class Favorites(
        val userId: Int
    ) : Routes()

    @Serializable
    data object Search : Routes()

    @Serializable
    data object Profile : Routes()
}