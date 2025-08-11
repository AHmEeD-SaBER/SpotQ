package com.spotq.authentication.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.core_ui.components.CustomBottomSurface
import com.example.core_ui.components.CustomButton
import com.example.core_ui.components.PageLayout
import com.example.core_ui.theme.SpotQTheme
import com.spotq.authentication.ui.R
import com.spotq.authentication.ui.components.CustomIconButton
import com.spotq.authentication.ui.components.CustomTextField
import com.spotq.authentication.ui.components.DontOrHaveAccount
import com.spotq.authentication.ui.components.PageHeader
import com.spotq.authentication.ui.components.Separator
import com.spotq.authentication.ui.login.components.ForgetPassword
import com.spotq.core_ui.R as CoreUiR

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    state: LoginContract.State,
    onEvent: (LoginContract.Event) -> Unit
) {
    PageLayout(
        modifier = modifier,
        imageRes = R.drawable.login_res,
        imageContentDescription = "Login illustration",
    ) {
        CustomBottomSurface(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(0.75f)
        ) {
            Column(
                modifier = Modifier.padding(
                    horizontal = dimensionResource(CoreUiR.dimen.padding_xl),
                    vertical = dimensionResource(CoreUiR.dimen.padding_xxl)
                )
            ) {
                PageHeader(
                    title = stringResource(CoreUiR.string.login_title),
                    subtitle = stringResource(CoreUiR.string.login_subtitle)
                )
                Spacer(modifier = Modifier.padding(vertical = dimensionResource(CoreUiR.dimen.padding_md)))
                CustomTextField(
                    label = stringResource(CoreUiR.string.label_email),
                    value = state.email,
                    placeholder = stringResource(CoreUiR.string.placeholder_email),
                    onValueChange = { onEvent(LoginContract.Event.EmailChanged(it)) },
                    isError = state.emailError != null,
                    errorMessage = state.emailError
                )
                Spacer(modifier = Modifier.padding(vertical = dimensionResource(CoreUiR.dimen.padding_sm)))
                CustomTextField(
                    label = stringResource(CoreUiR.string.label_password),
                    value = state.password,
                    placeholder = stringResource(CoreUiR.string.placeholder_password),
                    onValueChange = { onEvent(LoginContract.Event.PasswordChanged(it)) },
                    isError = state.passwordError != null,
                    errorMessage = state.passwordError
                )
                Spacer(modifier = Modifier.padding(vertical = dimensionResource(CoreUiR.dimen.padding_sm)))
                ForgetPassword(
                    modifier = Modifier.align(Alignment.End),
                    onClick = { onEvent(LoginContract.Event.ForgotPasswordClicked) }
                )
                CustomButton(
                    text = stringResource(CoreUiR.string.action_login),
                    onClick = { onEvent(LoginContract.Event.LoginClicked) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = dimensionResource(CoreUiR.dimen.padding_xl)),
                    isLoading = state.isLoading,
                    enabled = state.isFormValid && !state.isLoading
                )
                Spacer(
                    modifier = Modifier.padding(vertical = dimensionResource(CoreUiR.dimen.padding_sm))
                )
                Separator()
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CustomIconButton(
                        icon = R.drawable.google_logo,
                        contentDescription = stringResource(CoreUiR.string.login_with_google),
                        onClick = {},
                    )
                    Spacer(modifier = Modifier.padding(horizontal = dimensionResource(CoreUiR.dimen.padding_sm)))
                    CustomIconButton(
                        icon = R.drawable.x_logo,
                        contentDescription = stringResource(CoreUiR.string.login_with_x),
                        onClick = {},

                        )

                }
                Spacer(modifier = Modifier.padding(vertical = dimensionResource(CoreUiR.dimen.padding_sm)))
                DontOrHaveAccount(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    leading = stringResource(CoreUiR.string.dont_have_account),
                    trailing = stringResource(CoreUiR.string.sign_up),
                    onClick = { onEvent(LoginContract.Event.NavigateToSignup) },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    SpotQTheme {
        LoginScreen(
            state = LoginContract.State(
                email = "",
                password = "",
                emailError = null,
                passwordError = null
            ),
            onEvent = {}
        )
    }
}
