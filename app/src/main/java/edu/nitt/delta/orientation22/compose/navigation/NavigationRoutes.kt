package edu.nitt.delta.orientation22.compose.navigation

sealed class NavigationRoutes(val route: String) {
    object Login : NavigationRoutes("login")
    object Dashboard : NavigationRoutes("dashboard")
    object Map : NavigationRoutes("maps")
    object LeaderBoard : NavigationRoutes("leaderBoard")
}
