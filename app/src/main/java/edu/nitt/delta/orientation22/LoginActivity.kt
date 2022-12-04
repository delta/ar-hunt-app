package edu.nitt.delta.orientation22

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.nitt.delta.orientation22.compose.navigation.NavigationOuter
import edu.nitt.delta.orientation22.compose.navigation.NavigationRoutes
import edu.nitt.delta.orientation22.di.viewModel.actions.LoginAction
import edu.nitt.delta.orientation22.di.viewModel.uiState.LoginStateViewModel
import edu.nitt.delta.orientation22.di.viewModel.uiState.TeamStateViewModel
import edu.nitt.delta.orientation22.ui.theme.Orientation22androidTheme

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    companion object{
        var startDestination: String =NavigationRoutes.Login.route
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val teamViewModel by viewModels<TeamStateViewModel>()
        val loginViewModel by viewModels<LoginStateViewModel> ()
        setContent {
            Orientation22androidTheme() {
                val navController = rememberNavController()
                NavigationOuter(navController = navController, teamStateViewModel = teamViewModel, loginStateViewModel = loginViewModel)
            }
        }
    }
}
