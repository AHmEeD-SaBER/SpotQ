package com.spotq.onboarding.components

import PageIndicatorDot
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core_ui.R


@Composable
fun PageIndicator(
    modifier: Modifier = Modifier,
    currentPage: Int,
    totalPages: Int
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = dimensionResource(R.dimen.padding_md))
    ) {
        repeat(totalPages) { index ->
            PageIndicatorDot(
                isSelected = index == currentPage,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PageIndicatorPreview() {
    PageIndicator(
        currentPage = 1,
        totalPages = 3,
        modifier = Modifier.padding(16.dp)
    )
}

