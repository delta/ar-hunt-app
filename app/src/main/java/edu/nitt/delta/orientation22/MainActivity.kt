package edu.nitt.delta.orientation22

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.nitt.delta.orientation22.compose.navigation.BottomBar
import edu.nitt.delta.orientation22.compose.navigation.Navigation
import edu.nitt.delta.orientation22.ui.theme.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("RememberReturnType", "UnusedMaterial3ScaffoldPaddingParameter")
    @RequiresApi(Build.VERSION_CODES.M)
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Orientation22androidTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        val checkedState = remember { mutableStateOf(false) }
                        BottomBar(checkedState = checkedState, navController = navController)
                    }
                ) {
                    Navigation(navController = navController)
                }
            }
        }
    }
}
