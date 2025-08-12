package com.example.errors

import androidx.annotation.StringRes

sealed class CustomError(
    @StringRes val titleRes: Int,
    @StringRes val subtitleRes: Int,
    val originalException: Throwable? = null
) : Exception() {

    data class NoNetwork(
        @StringRes val title: Int = R.string.error_no_network_title,
        @StringRes val subtitle: Int = R.string.error_no_network_subtitle
    ) : CustomError(title, subtitle)

    data class NoData(
        @StringRes val title: Int = R.string.error_no_data_title,
        @StringRes val subtitle: Int = R.string.error_no_data_subtitle
    ) : CustomError(title, subtitle)

    data class Unknown(
        @StringRes val title: Int = R.string.error_unknown_title,
        @StringRes val subtitle: Int = R.string.error_unknown_subtitle,
        val exception: Throwable? = null
    ) : CustomError(title, subtitle, exception)
}