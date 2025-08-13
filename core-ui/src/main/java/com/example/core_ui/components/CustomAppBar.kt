package com.example.core_ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.core_ui.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAppBar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit = {},
    showSearchBar: Boolean = false,
    searchBar: @Composable (() -> Unit)? = null
) {
    Column(modifier) {
        TopAppBar(
            title = title,
            actions = actions,
            modifier = Modifier,
            windowInsets = TopAppBarDefaults.windowInsets
        )
        Spacer(
            modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_sm))
        )
        Box(modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_sm))) {
            if (showSearchBar && searchBar != null) {
                searchBar()
            }
        }

    }
}
