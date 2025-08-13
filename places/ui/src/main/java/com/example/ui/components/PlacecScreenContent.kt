package com.example.ui.components


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.core_ui.components.SectionHeader
import com.example.core_ui.theme.AppTypography
import com.example.ui.PlacesContract
import com.example.core_ui.R as coreUiR

@Composable
fun PlacesScreenContent(
    modifier: Modifier = Modifier,
    state: PlacesContract.State,
    onEvent: (PlacesContract.Events) -> Unit
) {
    Column(modifier.fillMaxSize().padding(horizontal = dimensionResource(coreUiR.dimen.padding_md))) {

        Spacer(modifier = Modifier.padding(vertical = dimensionResource(coreUiR.dimen.padding_md)))
        SectionHeader(
            title = stringResource(coreUiR.string.popular_nearby),
            titleStyle = AppTypography.sh8,
            trailing = stringResource(coreUiR.string.see_all),
            showTrailing = true,
            trailingStyle = AppTypography.bt7,
            modifier = Modifier.padding(bottom = dimensionResource(coreUiR.dimen.padding_sm))
        )
        HorizontalList(
            items = state.places.filter {
                it.hasImage || it.name.isNotBlank()
            },
            onEvent = onEvent,
        )

    }
}


@Preview(showBackground = true)
@Composable
fun PlacesScreenContentPreview() {
    PlacesScreenContent(
        state = PlacesContract.State(),
        onEvent = {}
    )
}