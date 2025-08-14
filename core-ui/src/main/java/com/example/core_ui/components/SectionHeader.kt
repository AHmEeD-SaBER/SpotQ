package com.example.core_ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SectionHeader(
    modifier: Modifier = Modifier,
    title: String,
    trailing: String? = null,
    onEvent: () -> Unit = {},
    showTrailing: Boolean = false,
    titleStyle: androidx.compose.ui.text.TextStyle,
    trailingStyle: androidx.compose.ui.text.TextStyle? = null,
) {
    Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = title, style = titleStyle)
        if (showTrailing && trailing != null) {
            if (trailingStyle != null) {
                Text(
                    text = trailing,
                    style = trailingStyle,
                    modifier = Modifier.clickable { onEvent() },
                    color = MaterialTheme.colorScheme.primary
                )

            }
        }
    }


}

@Preview(showBackground = true)
@Composable
fun SectionHeaderPreview() {
    SectionHeader(
        title = "Popular Nearby",
        trailing = "See All",
        showTrailing = true,
        titleStyle = androidx.compose.ui.text.TextStyle.Default,
        trailingStyle = androidx.compose.ui.text.TextStyle.Default,
        onEvent = {}
    )
}