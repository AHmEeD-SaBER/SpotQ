import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.core_ui.theme.AppTypography

@Composable
fun OnboardingDescription(modifier: Modifier = Modifier, description: String) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Text(
            text = description,
            textAlign = TextAlign.Center,
            style = AppTypography.bt3,
        )
    }

}

@Preview(showBackground = true)
@Composable
fun OnboardingDescriptionPreview() {
    OnboardingDescription(
        description = "Discover the best music and podcasts tailored just for you. Join us on a journey of sound and stories."
    )
}