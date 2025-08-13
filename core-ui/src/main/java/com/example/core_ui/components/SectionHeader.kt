package com.example.core_ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SectionHeader(
    modifier: Modifier = Modifier,
    title: String,
    trailing: String? = null,
    showTrailing: Boolean = false,
    titleStyle: androidx.compose.ui.text.TextStyle,
    trailingStyle: androidx.compose.ui.text.TextStyle? = null,
) {
    Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = title, style = titleStyle)
        if (showTrailing && trailing != null) {
            if (trailingStyle != null) {
                Text(text = trailing, style = trailingStyle)
            }
        }
    }


}