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

@Composable
fun LeaderBoardFragment(
    leaderBoardViewModel : LeaderBoardStateViewModel
){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ){
        leaderBoardViewModel.doAction(LeaderBoardAction.GetLeaderBoard)
        val leaderBoardData = leaderBoardViewModel.leaderBoardData.value
        LeaderBoardScreen(leaderBoardData = leaderBoardData,
            painter = painterResource(id = R.drawable.background_image),
            contentDescription = "LeaderBoard")
    }
}
