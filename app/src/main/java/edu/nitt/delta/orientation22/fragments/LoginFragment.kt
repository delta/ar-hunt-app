package edu.nitt.delta.orientation22.fragments

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import edu.nitt.delta.orientation22.R
import edu.nitt.delta.orientation22.compose.navigation.NavigationRoutes
import edu.nitt.delta.orientation22.compose.screens.LoginScreen
import edu.nitt.delta.orientation22.di.viewModel.uiState.LoginState
import edu.nitt.delta.orientation22.di.viewModel.uiState.LoginStateViewModel

@Composable
fun LoginFragment(
    navController: NavController,
    loginStateViewModel: LoginStateViewModel
){
    val painter= painterResource(id = R.drawable.background_image)
    val description="Background"
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ){
        val state= loginStateViewModel.uiState
        LoginScreen(painter,
            description,
            navController = navController,
            state = state.value,
            onSuccess = {
                navController.navigate(NavigationRoutes.TeamDetails.route){
                    popUpTo(NavigationRoutes.Login.route){inclusive=true}
                }
                loginStateViewModel.uiState.value=LoginState.IDLE
            },
            onFailure = {
                loginStateViewModel.uiState.value=LoginState.IDLE
                loginStateViewModel.error.toString()
            }
        )
    }
}
