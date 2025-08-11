package com.spotq.onboarding

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.core_ui.theme.SpotQTheme
import com.example.core_ui.utils.Constants.ANIMATION_DURATION_MEDIUM
import com.example.core_ui.R as CoreUiR

data class OnboardingData(
    val title: String,
    val description: String,
    val drawableRes: Int
)

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    onOnboardingComplete: () -> Unit = {}
) {
    val onboardingPages = listOf(
        OnboardingData(
            title = stringResource(R.string.onboarding_1_title),
            description = stringResource(R.string.onboarding_1_description),
            drawableRes = R.drawable.onboarding_1
        ),
        OnboardingData(
            title = stringResource(R.string.onboarding_2_title),
            description = stringResource(R.string.onboarding_2_description),
            drawableRes = R.drawable.onboarding_2
        ),
        OnboardingData(
            title = stringResource(R.string.onboarding_3_title),
            description = stringResource(R.string.onboarding_3_description),
            drawableRes = R.drawable.onboarding_3
        )
    )

    var currentPage by remember { mutableIntStateOf(0) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        AnimatedContent(
            targetState = currentPage,
            modifier = Modifier.weight(1f),
            transitionSpec = {
                fadeIn(
                    animationSpec = tween(durationMillis = ANIMATION_DURATION_MEDIUM.toInt())
                ) togetherWith fadeOut(
                    animationSpec = tween(durationMillis = ANIMATION_DURATION_MEDIUM.toInt())
                )
            },
            label = stringResource(CoreUiR.string.onboarding_label)
        ) { page ->
            val isLastPage = page == onboardingPages.size - 1

            OnboardingPage(
                title = onboardingPages[page].title,
                description = onboardingPages[page].description,
                drawableRes = onboardingPages[page].drawableRes,
                buttonText = if (isLastPage) {
                    stringResource(CoreUiR.string.action_finish)
                } else {
                    stringResource(CoreUiR.string.action_next)
                },
                currentPage = page,
                totalPages = onboardingPages.size,
                onButtonClick = {
                    if (isLastPage) {
                        onOnboardingComplete()
                    } else {
                        currentPage = page + 1
                    }
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview() {
    SpotQTheme {
        OnboardingScreen()
    }
}


