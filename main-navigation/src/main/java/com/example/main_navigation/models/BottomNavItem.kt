package com.example.main_navigation.models

data class BottomNavItem(
    val route: Any, // you can keep it as Routes.* sealed class
    val label: Int,
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val isSelected: Boolean = false
)






