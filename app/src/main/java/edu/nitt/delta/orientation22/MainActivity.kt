package edu.nitt.delta.orientation22

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.nitt.delta.orientation22.compose.navigation.BottomBar
import edu.nitt.delta.orientation22.compose.navigation.NavigationInner
import edu.nitt.delta.orientation22.di.viewModel.actions.TeamAction
import edu.nitt.delta.orientation22.di.viewModel.uiState.*
import edu.nitt.delta.orientation22.ui.theme.*
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("RememberReturnType", "UnusedMaterial3ScaffoldPaddingParameter")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mapViewModel by viewModels<MapStateViewModel>()
        val teamViewModel by viewModels<TeamStateViewModel>()
        val loginViewModel by viewModels<LoginStateViewModel>()
        val leaderBoardViewModel by viewModels<LeaderBoardStateViewModel>()
        setContent {
            val navController = rememberNavController()
            Orientation22androidTheme() {
                val checkedState = remember { mutableStateOf(false) }
                Scaffold(
                    bottomBar = {
                        BottomBar(checkedState = checkedState, navController = navController)
                    }
                ) {
                    NavigationInner(navController = navController, mapviewModel = mapViewModel, loginStateViewModel = loginViewModel, leaderBoardStateViewModel = leaderBoardViewModel, teamStateViewModel = teamViewModel )
                }
            }
        }
    }

    override fun onBackPressed() {
        finishAffinity()
        return
        super.onBackPressed()
    }

}
