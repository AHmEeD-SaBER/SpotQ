package com.example.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.core_ui.R
import com.example.core_ui.components.CustomTextField

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit,
    value: String = "",
) {
    CustomTextField(
        modifier = modifier,
        label = null,
        value = value,
        placeholder = stringResource(R.string.cd_search),
        onValueChange = onSearch,
        isError = false,
        errorMessage = null,
        isPasswordField = false,
        isPasswordVisible = false,
        trailingIcon = null,
        leadingIcon = {
            Icon(
                modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_xl)),
                painter = painterResource(com.example.ui.R.drawable.search_icon),
                contentDescription = stringResource(R.string.cd_search)
            )
        },
        contentPadding = PaddingValues(
            horizontal = dimensionResource(R.dimen.padding_md),
            vertical = dimensionResource(R.dimen.padding_xs)
        )

    )
}

@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    SearchBar(
        value = "Search",
        onSearch = {}
    )
}