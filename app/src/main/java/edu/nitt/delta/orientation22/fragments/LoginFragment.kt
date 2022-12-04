package edu.nitt.delta.orientation22.fragments

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import edu.nitt.delta.orientation22.LiveActivity
import edu.nitt.delta.orientation22.MainActivity
import edu.nitt.delta.orientation22.R
import edu.nitt.delta.orientation22.compose.navigation.NavigationRoutes
import edu.nitt.delta.orientation22.compose.screens.LoginScreen
import edu.nitt.delta.orientation22.di.viewModel.actions.LoginAction
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
        val isReg =loginStateViewModel.isRegistered
        var mContext = LocalContext.current
        Log.d("islive","before ${loginStateViewModel.isLive.value}")
        Log.d("islive","after ${loginStateViewModel.isLive.value}")
        val isLive = loginStateViewModel.isLive.value
        LoginScreen(painter,
            description,
            navController = navController,
            state = state.value,
            onSuccess = {
                if(!isReg.value.isRegistered) {
                    navController.navigate(NavigationRoutes.TeamDetails.route) {
                        popUpTo(NavigationRoutes.Login.route) { inclusive = true }
                    }
                } else {
                    if(isLive) {
                        mContext.startActivity(Intent(mContext, MainActivity::class.java))
                    }
                    else{
                        mContext.startActivity(Intent(mContext, LiveActivity::class.java))
                    }
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
