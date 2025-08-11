package com.spotq.onboarding.components

import OnboardingDescription
import OnboardingTitle
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import com.example.core_ui.theme.SpotQTheme
import com.example.core_ui.R

@Composable
fun OnboardingBottomSurface(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    buttonText: String = stringResource(id = R.string.action_next),
    currentPage: Int = 0,
    totalPages: Int = 3,
    onButtonClick: () -> Unit = {}
) {
    CustomBottomSurface(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f)
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_xl)),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OnboardingTitle(
                modifier = Modifier.weight(1f),
                title = title
            )

            OnboardingDescription(
                modifier = Modifier.weight(1f),
                description = description
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = dimensionResource(R.dimen.padding_xl)),
                contentAlignment = Alignment.Center
            ) {
                CustomButton(
                    text = buttonText,
                    onClick = onButtonClick
                )
            }
            PageIndicator(
                currentPage = currentPage,
                totalPages = totalPages
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun OnboardingBottomSurfacePreview() {
    SpotQTheme {
        OnboardingBottomSurface(
            title = "Get Started",
            description = "You're all set! Let's begin your amazing journey with SpotQ and explore all the features we have to offer."
        )
    }
}