package edu.nitt.delta.orientation22
import android.graphics.Color
import android.media.Image
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import edu.nitt.delta.orientation22.compose.navigation.Navigation
import edu.nitt.delta.orientation22.compose.screens.LoginScreen
import edu.nitt.delta.orientation22.ui.theme.Orientation22androidTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Orientation22androidTheme {
                Navigation()
            }
        }
    }
}
