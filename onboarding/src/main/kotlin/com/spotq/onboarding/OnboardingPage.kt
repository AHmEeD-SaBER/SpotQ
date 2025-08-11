package com.spotq.onboarding

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.core_ui.components.PageLayout
import com.example.core_ui.theme.SpotQTheme
import com.spotq.onboarding.components.OnboardingBottomSurface
import com.spotq.core_ui.R as CoreUiR

@Composable
fun OnboardingPage(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    @DrawableRes drawableRes: Int,
    buttonText: String = stringResource(id = CoreUiR.string.action_next),
    currentPage: Int = 0,
    totalPages: Int = 3,
    onButtonClick: () -> Unit = {},
) {
    PageLayout(
        modifier = modifier,
        imageRes = drawableRes,
        imageContentDescription = "Onboarding illustration",
        bottomContent = {
            OnboardingBottomSurface(
                title = title,
                description = description,
                buttonText = buttonText,
                currentPage = currentPage,
                totalPages = totalPages,
                onButtonClick = onButtonClick,
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun OnboardingPagePreview() {
    SpotQTheme {
        OnboardingPage(
            title = "Welcome to SpotQ",
            description = "Discover amazing features and get started with your journey.",
            drawableRes = R.drawable.onboarding_1
        )
    }
}
