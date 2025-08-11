package com.example.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.*
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseOut
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import kotlinx.coroutines.delay
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import com.example.core_ui.utils.Constants
import com.example.core_ui.R as CoreR
import com.example.core_ui.theme.SpotQTheme

@Composable
fun SplashScreen(onSplashFinished: () -> Unit) {
    val offsetX = remember { Animatable(-50f) }
    var showText by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(Constants.ANIMATION_DURATION_LONG + Constants.ANIMATION_DURATION_MEDIUM)
        launch {
            offsetX.animateTo(
                targetValue = -20f,
                animationSpec = tween(durationMillis = Constants.ANIMATION_DURATION_LONG.toInt(), easing = EaseOut)
            )
        }
        showText = true
        delay(Constants.SPLASH_DELAY_DURATION - Constants.ANIMATION_DURATION_LONG)
        onSplashFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy((-6).dp)
        ) {
            AnimatedVisibility(visible = showText) {
                Text(
                    text = stringResource(id = CoreR.string.brand_spot),
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontSize = dimensionResource(id = CoreR.dimen.text_size_headline).value.sp,
                        fontWeight = FontWeight.Black
                    ),
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(start = dimensionResource(id = CoreR.dimen.splash_logo_offset))
                )
            }

            Image(
                painter = painterResource(id = R.drawable.logo_q),
                contentDescription = stringResource(id = CoreR.string.cd_logo),
                contentScale = ContentScale.Inside,
                modifier = Modifier
                    .size(dimensionResource(id = CoreR.dimen.logo_size))
                    .wrapContentSize()
                    .graphicsLayer { translationX = offsetX.value }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SpotQTheme {
        SplashScreen(onSplashFinished = {
        })
    }
}
