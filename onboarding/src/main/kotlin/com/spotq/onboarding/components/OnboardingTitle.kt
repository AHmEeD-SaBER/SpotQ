import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.core_ui.theme.AppTypography

@Composable
fun OnboardingTitle(modifier: Modifier = Modifier, title: String) {
    Text(
        modifier = modifier,
        text = title,
        style = AppTypography.h7,
        textAlign = TextAlign.Center,
    )
}

@Preview(showBackground = true)
@Composable
fun OnboardingTitlePreview() {
    OnboardingTitle(
        title = "Welcome to SpotQ"
    )
}