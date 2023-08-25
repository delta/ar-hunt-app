package edu.nitt.delta.orientation22.compose.navigation

import edu.nitt.delta.orientation22.R
import edu.nitt.delta.orientation22.ui.theme.brightYellow
import edu.nitt.delta.orientation22.ui.theme.cyan
import edu.nitt.delta.orientation22.ui.theme.lightCyan
import edu.nitt.delta.orientation22.ui.theme.red

sealed class NavigationList(val navList: List<BottomNavItem>) {
    object NavList : NavigationList(
        listOf(
            BottomNavItem(
                name = "Home",
                route = NavigationRoutes.Dashboard.route,
                icon = R.drawable.home_screen_selected,
                color = lightCyan
            ),
            BottomNavItem(
                name = "Map",
                route = NavigationRoutes.Map.route,
                icon = R.drawable.map_screen_selected,
                color = red
            ),
            BottomNavItem(
                name = "Leaderboard",
                route = NavigationRoutes.LeaderBoard.route,
                icon = R.drawable.leaderboard_selected,
                color = brightYellow
            ),
        ),
    )
}