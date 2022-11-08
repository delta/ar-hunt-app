package edu.nitt.delta.orientation22.compose.navigation

data class BottomNavItem(
    val name: String,
    val route: String,
    val selectedIcon: Int,
    val unselectedIcon: Int,
)