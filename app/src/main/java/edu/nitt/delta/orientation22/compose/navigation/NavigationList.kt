package edu.nitt.delta.orientation22.compose.navigation

import edu.nitt.delta.orientation22.R

sealed class NavigationList(val navList: List<BottomNavItem>) {
    object NavList : NavigationList(
        listOf(
        BottomNavItem(
            name = "Home",
            route = NavigationRoutes.Dashboard.route,
            selectedIcon = R.drawable.home_screen_selected,
            unselectedIcon = R.drawable.home_screen_unselected
        ),
        BottomNavItem(
            name = "Leaderboard",
            route = NavigationRoutes.LeaderBoard.route,
            selectedIcon = R.drawable.leaderboard_selected,
            unselectedIcon = R.drawable.leaderboard_unselected
        ),
        ),
    )
}