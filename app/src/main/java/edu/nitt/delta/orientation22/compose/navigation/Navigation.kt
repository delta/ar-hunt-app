package edu.nitt.delta.orientation22.compose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.gson.Gson
import edu.nitt.delta.orientation22.di.viewModel.uiState.ArStateViewModel
import edu.nitt.delta.orientation22.di.viewModel.uiState.LeaderBoardStateViewModel
import edu.nitt.delta.orientation22.di.viewModel.uiState.MapStateViewModel
import edu.nitt.delta.orientation22.di.viewModel.uiState.TeamStateViewModel
import edu.nitt.delta.orientation22.fragments.*
import edu.nitt.delta.orientation22.models.Team
import edu.nitt.delta.orientation22.models.TeamMember

@Composable
fun NavigationOuter(navController: NavHostController,teamStateViewModel:TeamStateViewModel){
    NavHost(
        navController = navController,
        startDestination = NavigationRoutes.Login.route,
    ) {
        composable(route = NavigationRoutes.Login.route){
            LoginFragment(navController)
        }
        composable(route = NavigationRoutes.Dashboard.route){
            DashboardFragment(teamStateViewModel)
        }
        composable(route = NavigationRoutes.TeamDetails.route){
            TeamDetailsFragment(teamStateViewModel)
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
