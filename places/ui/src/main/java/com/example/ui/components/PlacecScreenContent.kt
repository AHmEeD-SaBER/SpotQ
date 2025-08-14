package com.example.ui.components


import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.core_ui.components.SectionHeader
import com.example.core_ui.theme.AppTypography
import com.example.ui.places.PlacesContract
import com.example.core_ui.R as coreUiR

@Composable
fun PlacesScreenContent(
    modifier: Modifier = Modifier,
    state: PlacesContract.State,
    onEvent: (PlacesContract.Events) -> Unit
) {
    LazyColumn(
        modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(coreUiR.dimen.padding_md))
    ) {
        val (firstGroup, secondGroup) = state.places.partition { it.rate < 3 }

        item {
            Spacer(modifier = Modifier.padding(vertical = dimensionResource(coreUiR.dimen.padding_md)))
        }
        item {
            SectionHeader(
                title = stringResource(coreUiR.string.popular_nearby),
                titleStyle = AppTypography.sh8,
                trailing = stringResource(coreUiR.string.see_all),
                showTrailing = true,
                trailingStyle = AppTypography.bt7,
                modifier = Modifier.padding(bottom = dimensionResource(coreUiR.dimen.padding_sm))
            )
        }
        item {
            HorizontalList(
                items = firstGroup.filter {
                    it.hasImage || it.name.isNotBlank()
                },
                onEvent = onEvent,
            )
        }
        item {
            Spacer(modifier = Modifier.padding(vertical = dimensionResource(coreUiR.dimen.padding_md)))
        }
        item {
            SectionHeader(
                title = stringResource(coreUiR.string.recommended_places),
                titleStyle = AppTypography.sh8,
                trailing = stringResource(coreUiR.string.see_all),
                showTrailing = true,
                trailingStyle = AppTypography.bt7,
                modifier = Modifier.padding(bottom = dimensionResource(coreUiR.dimen.padding_sm))
            )
        }
        item {
            VerticalList(
                items = secondGroup.filter {
                    it.hasImage || it.name.isNotBlank()
                },
                onEvent = onEvent,
            )
        }


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