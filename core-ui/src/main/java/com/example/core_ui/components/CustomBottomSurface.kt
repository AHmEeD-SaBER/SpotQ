package com.example.core_ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.dimensionResource
import com.spotq.core_ui.R

@Composable
fun CustomBottomSurface(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(
        topStart = dimensionResource(R.dimen.surface_corner_radius),
        topEnd = dimensionResource(R.dimen.surface_corner_radius)
    ),
    color: Color = MaterialTheme.colorScheme.surface,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier,
        shape = shape,
        color = color,
        content = content
    )
}
