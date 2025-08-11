package com.spotq.authentication.ui.components

import android.service.autofill.CustomDescription
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import com.example.core_ui.utils.Constants
import com.example.core_ui.R as CoreUiR

@Composable
fun CustomIconButton(
    modifier: Modifier = Modifier,
    icon: Int,
    onClick: () -> Unit,
    contentDescription: String = ""
) {
    Box(
        modifier
            .clickable { onClick.invoke() }
            .height(dimensionResource(CoreUiR.dimen.iconbtn_height))
            .width(
                dimensionResource(CoreUiR.dimen.iconbtn_width)
            )
            .background(Color.Transparent)
            .border(
                width = dimensionResource(CoreUiR.dimen.border_width_thin),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = Constants.ALPHA_MEDIUM),
                shape = RoundedCornerShape(dimensionResource(CoreUiR.dimen.button_corner_radius))
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = contentDescription,
        )

    }


}