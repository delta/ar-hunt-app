package edu.nitt.delta.orientation22.fragments

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import edu.nitt.delta.orientation22.R
import edu.nitt.delta.orientation22.compose.screens.LeaderBoardScreen
import edu.nitt.delta.orientation22.di.viewModel.actions.LeaderBoardAction
import edu.nitt.delta.orientation22.di.viewModel.actions.TeamAction
import edu.nitt.delta.orientation22.di.viewModel.uiState.LeaderBoardStateViewModel
import edu.nitt.delta.orientation22.di.viewModel.uiState.TeamStateViewModel

@Composable
fun LeaderBoardFragment(
    leaderBoardViewModel : LeaderBoardStateViewModel,
    teamStateViewModel : TeamStateViewModel
){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ){
        leaderBoardViewModel.doAction(LeaderBoardAction.GetLeaderBoard)
        teamStateViewModel.doAction(TeamAction.GetTeam)
        val leaderBoardData = leaderBoardViewModel.leaderBoardData.value
        val teamData = teamStateViewModel.teamData.value

        LeaderBoardScreen(
            leaderBoardData = leaderBoardData,
            currentTeamData = teamData,
            painter = painterResource(id = R.drawable.background),
            contentDescription = "LeaderBoard"
        )
    }
}