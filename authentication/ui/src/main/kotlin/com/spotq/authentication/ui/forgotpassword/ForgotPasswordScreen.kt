package com.spotq.authentication.ui.forgotpassword

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.core_ui.components.CustomBottomSurface
import com.example.core_ui.components.CustomButton
import com.example.core_ui.components.PageLayout
import com.example.core_ui.theme.SpotQTheme
import com.example.core_ui.utils.Constants
import com.spotq.authentication.ui.R
import com.spotq.authentication.ui.components.CustomTextField
import com.spotq.authentication.ui.components.PageHeader
import com.example.core_ui.R as CoreUiR

@Composable
fun ForgotPasswordScreen(
    modifier: Modifier = Modifier,
    state: ForgotPasswordContract.State,
    onEvent: (ForgotPasswordContract.Event) -> Unit
) {

    PageLayout(
        imageRes = R.drawable.forgot_password_res
    ) {
        CustomBottomSurface(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(Constants.AUTH_BOTTOM_SURFACE_HEIGHT)

        ) {
            Column(
                modifier = Modifier.padding(
                    vertical = dimensionResource(CoreUiR.dimen.padding_xxl),
                    horizontal = dimensionResource(CoreUiR.dimen.padding_lg)
                )
            ) {
                PageHeader(
                    title = stringResource(CoreUiR.string.forget_password),
                    subtitle = stringResource(CoreUiR.string.forget_password_subtitle)
                )
                Spacer(
                    modifier = Modifier.padding(dimensionResource(CoreUiR.dimen.padding_md))
                )
                CustomTextField(
                    value = state.email,
                    onValueChange = { onEvent(ForgotPasswordContract.Event.EmailChanged(it)) },
                    label = stringResource(CoreUiR.string.label_email),
                    errorMessage = state.emailError,
                    isError = state.emailError != null,
                )
                Spacer(
                    modifier = Modifier.padding(dimensionResource(CoreUiR.dimen.padding_lg))
                )
                CustomButton(
                    text = stringResource(CoreUiR.string.action_send),
                    onClick = { onEvent(ForgotPasswordContract.Event.SubmitClicked) },
                    isLoading = state.isLoading,
                    enabled = state.isFormValid
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun ForgotPasswordScreenPreview() {
    SpotQTheme {
        ForgotPasswordScreen(
            state = ForgotPasswordContract.State(),
            onEvent = {}
        )
    }

}