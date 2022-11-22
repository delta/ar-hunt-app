package edu.nitt.delta.orientation22.compose.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import edu.nitt.delta.orientation22.compose.screens.DAuthWebView
import edu.nitt.delta.orientation22.di.viewModel.actions.LoginAction
import edu.nitt.delta.orientation22.di.viewModel.actions.TeamAction
import edu.nitt.delta.orientation22.di.viewModel.uiState.*
import edu.nitt.delta.orientation22.fragments.*

@Composable
fun NavigationOuter(navController: NavHostController,teamStateViewModel:TeamStateViewModel,loginStateViewModel: LoginStateViewModel){
    NavHost(
        navController = navController,
        startDestination = if (loginStateViewModel.isLoggedIn) NavigationRoutes.TeamDetails.route else NavigationRoutes.Login.route,
    ) {
        composable(route = NavigationRoutes.Login.route){
            LoginFragment(navController,loginStateViewModel)
        }
//        composable(route = NavigationRoutes.Dashboard.route){
//            DashboardFragment(teamStateViewModel)
//        }
        composable(route = NavigationRoutes.TeamDetails.route){
            TeamDetailsFragment(teamStateViewModel)
        }
        composable(route = NavigationRoutes.DAuthWebView.route) {
            DAuthWebView(onSuccess = {
                loginStateViewModel.doAction(LoginAction.Login(it))
                navController.popBackStack()
            })
        }
    }
}

@Composable
fun NavigationInner(navController: NavHostController,mapviewModel: MapStateViewModel, arStateViewModel: ArStateViewModel,leaderBoardStateViewModel: LeaderBoardStateViewModel,teamStateViewModel: TeamStateViewModel){
    NavHost(
        navController = navController,
        startDestination = NavigationRoutes.Dashboard.route,
    ) {
        composable(route = NavigationRoutes.Dashboard.route){
            DashboardFragment(teamStateViewModel = teamStateViewModel)
        }
        composable(route = NavigationRoutes.Map.route){
            MapFragment(mapviewModel = mapviewModel)
        }
        composable(route = NavigationRoutes.LeaderBoard.route){
            LeaderBoardFragment(leaderBoardViewModel = leaderBoardStateViewModel)
        }
    }
}
