package com.example.core_ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.core_ui.R

@Composable
fun PageLayout(
    modifier: Modifier = Modifier,
    @DrawableRes imageRes: Int,
    imageContentDescription: String? = null,
    bottomContent: @Composable () -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.primary)
    ) {
        Image(
            modifier = Modifier
                .padding(top = dimensionResource(R.dimen.padding_xxl))
                .align(Alignment.TopCenter),
            painter = painterResource(imageRes),
            contentDescription = imageContentDescription ?: stringResource(id = R.string.image_content_description)
        )

        Box(
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            bottomContent()
        }
    }
}

@Preview
@Composable
fun PageLayoutPreview() {
    PageLayout(
        imageRes = R.drawable.img_placeholder,
        bottomContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_md)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Welcome to the App",
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_sm)))
                Text(
                    text = "Enjoy your experience!",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    )
}
