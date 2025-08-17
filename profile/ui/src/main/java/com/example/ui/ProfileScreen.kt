package com.example.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.core_ui.components.CustomAppBar
import com.example.core_ui.components.CustomBottomSurface
import com.example.core_ui.components.CustomButton
import com.example.core_ui.components.CustomTag
import com.example.core_ui.components.PageLayout
import com.example.core_ui.components.SectionHeader
import com.example.core_ui.theme.AppTypography
import com.example.core_ui.theme.SpotQTheme
import com.example.core_ui.utils.Constants
import com.example.core_ui.R as CoreUiR

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    state: ProfileContract.State,
    onEvent: (ProfileContract.Event) -> Unit
) {

    Box {
        PageLayout(
            modifier = modifier.fillMaxSize(),
        ) {
            CustomBottomSurface(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(Constants.AUTH_BOTTOM_SURFACE_HEIGHT)
            ) {
                Column(
                    modifier = Modifier.padding(
                        horizontal = dimensionResource(CoreUiR.dimen.padding_lg),
                        vertical = dimensionResource(CoreUiR.dimen.padding_xl)
                    )
                ) {

                    if (state.isLoading) {
                        CircularProgressIndicator()
                    } else if (state.error != null) {
                        Text(
                            text = stringResource(state.error),
                            style = AppTypography.sh5,
                            modifier = Modifier.padding(dimensionResource(CoreUiR.dimen.padding_md))
                        )
                    } else {
                        SectionHeader(
                            title = stringResource(CoreUiR.string.label_name),
                            showTrailing = false,
                            titleStyle = AppTypography.bt1
                        )
                        CustomTag(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(dimensionResource(CoreUiR.dimen.padding_sm)),
                            text = state.userName,
                            style = AppTypography.bt6.copy(textAlign = TextAlign.Center)
                        )
                        Spacer(modifier = Modifier.padding(vertical = dimensionResource(CoreUiR.dimen.padding_md)))
                        SectionHeader(
                            title = stringResource(CoreUiR.string.label_email),
                            showTrailing = false,
                            titleStyle = AppTypography.bt1
                        )
                        CustomTag(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(dimensionResource(CoreUiR.dimen.padding_sm)),
                            text = state.userEmail,
                            style = AppTypography.bt6.copy(textAlign = TextAlign.Center)
                        )
                        Spacer(modifier = Modifier.padding(vertical = dimensionResource(CoreUiR.dimen.padding_xxl)))
                        CustomButton(
                            text = stringResource(CoreUiR.string.label_logout),
                            isLoading = false,
                            onClick = { onEvent(ProfileContract.Event.Logout) },
                            modifier = Modifier
                                .fillMaxWidth(),
                            containerColor = MaterialTheme.colorScheme.error.copy(alpha = Constants.ALPHA_MD)

                        )
                    }
                }

            }


        }

        Text(
            text = stringResource(CoreUiR.string.label_profile),
            style = AppTypography.h7,
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(top = dimensionResource(CoreUiR.dimen.padding_sm), start = dimensionResource(CoreUiR.dimen.padding_md)),
        )

    }

}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    SpotQTheme {
        ProfileScreen(
            state = ProfileContract.State(
                userName = "John Doe",
                userEmail = "johndoe@gmial.com",
                isLoading = false,
                error = null
            ),
            onEvent = {}
        )
    }
}