package com.spotq.authentication.ui.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.core_ui.utils.Constants
import com.spotq.authentication.ui.R
import com.spotq.authentication.ui.components.CustomIconButton
import com.spotq.authentication.ui.components.CustomTextField
import com.spotq.authentication.ui.components.DontOrHaveAccount
import com.spotq.authentication.ui.components.PageHeader
import com.spotq.authentication.ui.components.Separator
import com.example.core_ui.R as CoreUiR

@Composable
fun SignupScreen(
    modifier: Modifier = Modifier,
    state: SignupContract.State,
    onEvent: (SignupContract.Event) -> Unit
) {
    PageLayout(
        modifier = modifier,
        imageRes = R.drawable.signup_res,
        bottomContent = {
            CustomBottomSurface(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(Constants.AUTH_BOTTOM_SURFACE_HEIGHT)
            ) {
                Column(
                    modifier = Modifier.padding(
                        horizontal = dimensionResource(CoreUiR.dimen.padding_xl),
                        vertical = dimensionResource(CoreUiR.dimen.padding_xxl)
                    )
                ) {
                    PageHeader(
                        title = stringResource(CoreUiR.string.signup_title),
                        subtitle = stringResource(CoreUiR.string.signup_subtitle)
                    )
                    Spacer(modifier = Modifier.padding(vertical = dimensionResource(CoreUiR.dimen.padding_md)))

                    CustomTextField(
                        label = stringResource(CoreUiR.string.label_name),
                        value = state.name,
                        placeholder = stringResource(CoreUiR.string.placeholder_name),
                        onValueChange = { onEvent(SignupContract.Event.NameChanged(it)) },
                        isError = state.nameError != null,
                        errorMessage = state.nameError
                    )
                    Spacer(modifier = Modifier.padding(vertical = dimensionResource(CoreUiR.dimen.padding_sm)))

                    CustomTextField(
                        label = stringResource(CoreUiR.string.label_email),
                        value = state.email,
                        placeholder = stringResource(CoreUiR.string.placeholder_email),
                        onValueChange = { onEvent(SignupContract.Event.EmailChanged(it)) },
                        isError = state.emailError != null,
                        errorMessage = state.emailError
                    )
                    Spacer(modifier = Modifier.padding(vertical = dimensionResource(CoreUiR.dimen.padding_sm)))

                    CustomTextField(
                        label = stringResource(CoreUiR.string.label_password),
                        value = state.password,
                        placeholder = stringResource(CoreUiR.string.placeholder_password),
                        onValueChange = { onEvent(SignupContract.Event.PasswordChanged(it)) },
                        isError = state.passwordError != null,
                        errorMessage = state.passwordError,
                        isPasswordField = true,
                        isPasswordVisible = state.isPasswordVisible,
                        trailingIcon = {
                            IconButton(onClick = { onEvent(SignupContract.Event.TogglePasswordVisibility) }) {
                                Icon(
                                    imageVector = if (state.isPasswordVisible)
                                        Icons.Filled.Visibility
                                    else
                                        Icons.Filled.VisibilityOff,
                                    contentDescription = if (state.isPasswordVisible)
                                        stringResource(CoreUiR.string.hide_password)
                                    else
                                        stringResource(CoreUiR.string.show_password),
                                )
                            }
                        }
                    )

                    CustomButton(
                        text = stringResource(CoreUiR.string.action_signup),
                        onClick = { onEvent(SignupContract.Event.SignupClicked) },
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
                            contentDescription = stringResource(CoreUiR.string.signup_with_google),
                            onClick = {},
                        )
                        Spacer(modifier = Modifier.padding(horizontal = dimensionResource(CoreUiR.dimen.padding_sm)))
                        CustomIconButton(
                            icon = R.drawable.x_logo,
                            contentDescription = stringResource(CoreUiR.string.signup_with_x),
                            onClick = {},
                        )
                    }
                    Spacer(modifier = Modifier.padding(vertical = dimensionResource(CoreUiR.dimen.padding_sm)))

                    DontOrHaveAccount(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        leading = stringResource(CoreUiR.string.already_have_account),
                        trailing = stringResource(CoreUiR.string.login_title),
                        onClick = { onEvent(SignupContract.Event.NavigateToLogin) },
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun SignupScreenPreview() {
    SpotQTheme {
        SignupScreen(
            state = SignupContract.State(
                name = "",
                email = "",
                password = "",
                nameError = null,
                emailError = null,
                passwordError = null
            ),
            onEvent = {}
        )
    }
}
