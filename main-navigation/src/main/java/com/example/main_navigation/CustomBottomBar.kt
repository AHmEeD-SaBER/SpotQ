package com.example.main_navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core_ui.theme.AppTypography
import com.example.core_ui.utils.Constants
import com.example.core_ui.utils.Routes
import com.example.main_navigation.models.BottomNavItem
import com.example.core_ui.R as CoreUiR

@Composable
fun CustomBottomBar(
    items: List<BottomNavItem>,
    onItemClick: (BottomNavItem) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(CoreUiR.dimen.padding_lg),
                vertical = dimensionResource(CoreUiR.dimen.padding_xxl)
            ),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = dimensionResource(CoreUiR.dimen.card_elevation),
        shadowElevation = dimensionResource(CoreUiR.dimen.card_elevation),
        shape = RoundedCornerShape(dimensionResource(CoreUiR.dimen.bottom_bar_corner_radius))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(CoreUiR.dimen.bottom_bar_height))
                .padding(horizontal = dimensionResource(CoreUiR.dimen.padding_sm)),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(dimensionResource(CoreUiR.dimen.padding_sm))
                        .clip(RoundedCornerShape(dimensionResource(CoreUiR.dimen.elipsed_corner_radius)))
                        .background(
                            if (item.isSelected)
                                MaterialTheme.colorScheme.primary.copy(alpha = Constants.ALPHA_LOW)
                            else
                                Color.Transparent
                        )
                        .clickable { onItemClick(item) },
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(
                            vertical = dimensionResource(CoreUiR.dimen.padding_xs),
                            horizontal = dimensionResource(CoreUiR.dimen.padding_sm)
                        )
                    ) {
                        Icon(
                            painter = painterResource(
                                if (item.isSelected) item.selectedIcon else item.unselectedIcon
                            ),
                            contentDescription = stringResource(item.label),
                            tint = if (item.isSelected)
                                MaterialTheme.colorScheme.tertiary
                            else
                                MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(dimensionResource(CoreUiR.dimen.icon_size_sm))
                        )
                        Text(
                            text = stringResource(item.label),
                            color = if (item.isSelected)
                                MaterialTheme.colorScheme.onSurfaceVariant
                            else
                                MaterialTheme.colorScheme.onSurfaceVariant,
                            style = AppTypography.bt9.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun CustomBottomBarPreview() {
    val items = listOf(
        BottomNavItem(
            route = Routes.Places,
            label = CoreUiR.string.label_home,
            selectedIcon = CoreUiR.drawable.home_icon_filled,
            unselectedIcon = CoreUiR.drawable.home_icon_outlined,
            isSelected = true // default
        ),
        BottomNavItem(
            route = Routes.Favorites(0), // you can inject userId later
            label = CoreUiR.string.label_favorites,
            selectedIcon = CoreUiR.drawable.love_icon_field,
            unselectedIcon = CoreUiR.drawable.love_icon_outlined
        ),
        BottomNavItem(
            route = Routes.Search,
            label = CoreUiR.string.label_search,
            selectedIcon = CoreUiR.drawable.search_filled,
            unselectedIcon = CoreUiR.drawable.search_outlined
        ),
        BottomNavItem(
            route = Routes.Profile(0),
            label = CoreUiR.string.label_profile,
            selectedIcon = CoreUiR.drawable.user_filled,
            unselectedIcon = CoreUiR.drawable.user_outlined
        )
    )

    CustomBottomBar(items) {}
}
