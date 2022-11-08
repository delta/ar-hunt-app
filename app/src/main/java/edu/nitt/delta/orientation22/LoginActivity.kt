package edu.nitt.delta.orientation22

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.nitt.delta.orientation22.compose.navigation.NavigationOuter
import edu.nitt.delta.orientation22.compose.navigation.NavigationRoutes

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavigationOuter(navController = navController)
            navController.navigate(NavigationRoutes.Login.route)
        }
    }
}
