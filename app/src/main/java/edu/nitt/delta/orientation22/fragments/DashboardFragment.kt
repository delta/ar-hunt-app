package edu.nitt.delta.orientation22.fragments

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.startActivity
import edu.nitt.delta.orientation22.LoginActivity
import edu.nitt.delta.orientation22.MainActivity
import edu.nitt.delta.orientation22.compose.screens.DashboardScreen
import edu.nitt.delta.orientation22.di.viewModel.actions.LoginAction
import edu.nitt.delta.orientation22.di.viewModel.actions.TeamAction
import edu.nitt.delta.orientation22.di.viewModel.uiState.LoginStateViewModel
import edu.nitt.delta.orientation22.di.viewModel.uiState.TeamStateViewModel
import edu.nitt.delta.orientation22.models.Team

@Composable
fun DashboardFragment(
    teamStateViewModel: TeamStateViewModel,
    loginStateViewModel: LoginStateViewModel
) {
    val mcontext =LocalContext.current

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ){
        teamStateViewModel.doAction(TeamAction.GetTeam)
        val teamData = teamStateViewModel.teamData.value
        Log.d("GetTeam","tema $teamData")
        DashboardScreen(team = teamData, logout = {
            loginStateViewModel.doAction(LoginAction.IsLoggedOut)
            val intent = Intent(mcontext, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            mcontext.startActivity(intent)

        })
    }
}
