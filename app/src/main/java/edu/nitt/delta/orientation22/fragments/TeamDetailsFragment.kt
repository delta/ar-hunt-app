package edu.nitt.delta.orientation22.fragments

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import edu.nitt.delta.orientation22.compose.screens.TeamDetailsScreen
import edu.nitt.delta.orientation22.di.viewModel.actions.TeamAction
import edu.nitt.delta.orientation22.di.viewModel.uiState.TeamStateViewModel

@Composable
fun TeamDetailsFragment(
    teamStateViewModel: TeamStateViewModel
){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ){
        teamStateViewModel.doAction(TeamAction.GetTeam)
        val teamData = teamStateViewModel.teamData
        Log.d("GetTeam","tema $teamData")
        TeamDetailsScreen(teamDetails = teamData) {
            teamStateViewModel.doAction(
                TeamAction.RegisterTeam(
                    it
                )
            )
        }
    }
}