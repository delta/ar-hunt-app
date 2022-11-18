package edu.nitt.delta.orientation22.fragments

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import edu.nitt.delta.orientation22.compose.screens.DashboardScreen
import edu.nitt.delta.orientation22.di.viewModel.uiState.TeamStateViewModel
import edu.nitt.delta.orientation22.models.Team

@Composable
fun DashboardFragment(teamStateViewModel: TeamStateViewModel,team: Team) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ){
        DashboardScreen(team = team)
    }
}
