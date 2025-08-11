import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.core_ui.theme.SpotQTheme
import com.spotq.core_ui.R

@Composable
fun OnboardingImage(modifier: Modifier = Modifier, drawableRes: Int) {
    Image(
        modifier = modifier.padding(top = dimensionResource(R.dimen.padding_xl)),
        painter = painterResource(drawableRes),
        contentDescription = stringResource(id = R.string.onboarding_image),
    )
}

@Preview(showBackground = true)
@Composable
fun OnboardingImagePreview() {
    SpotQTheme {
        OnboardingImage(
            drawableRes = com.spotq.onboarding.R.drawable.onboarding_1
        )
    }
}