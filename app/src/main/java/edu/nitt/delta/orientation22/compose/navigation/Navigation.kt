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

            var data = Gson().fromJson(it.arguments!!.getString("Team"), Team::class.java)
            if (data == null){
                data = Team(leader = TeamMember("Leader", "106121000", true), members = mutableListOf(
                    TeamMember("Member - 1", "106121000"),
                    TeamMember("Member - 2", "106121000"),
                    TeamMember("Member - 3", "106121000"),
                ),
                teamName = "Team Name")
            }
            DashboardFragment(teamStateViewModel = teamStateViewModel,data)
        }
        composable(route = NavigationRoutes.TeamDetails.route){
            TeamDetailsFragment(teamStateViewModel)
        }
    }
}

@Composable
fun NavigationInner(navController: NavHostController,mapviewModel: MapStateViewModel,arStateViewModel: ArStateViewModel,leaderBoardStateViewModel: LeaderBoardStateViewModel,teamStateViewModel: TeamStateViewModel){
    NavHost(
        navController = navController,
        startDestination = NavigationRoutes.Dashboard.route,
    ) {
        composable(route = NavigationRoutes.Dashboard.route){
            if (it.arguments != null) {
                var data = Gson().fromJson(it.arguments!!.getString("Team"), Team::class.java)
                if (data == null) {
                    data = Team(
                        leader = TeamMember("Leader", "106121000", true), members = mutableListOf(
                            TeamMember("Member - 1", "106121000"),
                            TeamMember("Member - 2", "106121000"),
                            TeamMember("Member - 3", "106121000"),
                        ),
                        teamName = "Team Name"
                    )
                }
                DashboardFragment(teamStateViewModel = teamStateViewModel,data)
            }
            else {
                val data = Team(
                    leader = TeamMember("Leader", "106121000", true), members = mutableListOf(
                        TeamMember("Member - 1", "106121000"),
                        TeamMember("Member - 2", "106121000"),
                        TeamMember("Member - 3", "106121000"),
                    ),
                    teamName = "Team Name"
                )
                DashboardFragment(teamStateViewModel = teamStateViewModel,data)
            }
        }
        composable(route = NavigationRoutes.Map.route){
            MapFragment(mapviewModel = mapviewModel)
        }
        composable(route = NavigationRoutes.LeaderBoard.route){
            LeaderBoardFragment(leaderBoardViewModel = leaderBoardStateViewModel)
        }
    }
}
