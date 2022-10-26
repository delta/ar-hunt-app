package edu.nitt.delta.orientation22.compose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.nitt.delta.orientation22.fragments.DashboardFragment
import edu.nitt.delta.orientation22.fragments.LeaderBoardFragment
import edu.nitt.delta.orientation22.fragments.LoginFragment
import edu.nitt.delta.orientation22.fragments.MapFragment


@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NavigationRoutes.Login.route,
    ) {
        composable(route = NavigationRoutes.Login.route){
            LoginFragment()
        }
        composable(route = NavigationRoutes.Dashboard.route){
            DashboardFragment()
        }
        composable(route = NavigationRoutes.Map.route){
            MapFragment()
        }
        composable(route = NavigationRoutes.LeaderBoard.route){
            LeaderBoardFragment()
        }
    }
}
