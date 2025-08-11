import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core_ui.theme.SpotQTheme
import com.example.core_ui.R

@Composable
fun PageIndicatorDot(
    modifier: Modifier = Modifier,
    isSelected: Boolean
) {
    Box(
        modifier = modifier
            .padding(horizontal = dimensionResource(R.dimen.padding_xs))
            .size(
                width = dimensionResource(R.dimen.dotted_indicator_width),
                height = dimensionResource(R.dimen.dotted_indicator_height)
            )
            .clip(
                androidx.compose.foundation.shape.CircleShape

            ),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = if (isSelected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
            }
        ) {}
    }
}

@Preview(showBackground = true)
@Composable
fun PageIndicatorDotPreview() {
    SpotQTheme {
        Row {
            PageIndicatorDot(isSelected = true)
            Spacer(modifier = Modifier.width(8.dp))
            PageIndicatorDot(isSelected = false)
            Spacer(modifier = Modifier.width(8.dp))
            PageIndicatorDot(isSelected = false)
        }
    }
}