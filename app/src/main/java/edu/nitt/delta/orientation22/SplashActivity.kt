package edu.nitt.delta.orientation22

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import dagger.hilt.android.AndroidEntryPoint
import edu.nitt.delta.orientation22.di.viewModel.actions.LoginAction
import edu.nitt.delta.orientation22.di.viewModel.actions.TeamAction
import edu.nitt.delta.orientation22.di.viewModel.uiState.LoginStateViewModel
import edu.nitt.delta.orientation22.di.viewModel.uiState.TeamStateViewModel
import edu.nitt.delta.orientation22.ui.theme.Orientation22androidTheme
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds


@AndroidEntryPoint
class SplashActivity : ComponentActivity() {
    val teamStateViewModel by viewModels<TeamStateViewModel> ()
    val loginStateViewModel by viewModels<LoginStateViewModel> ()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Orientation22androidTheme {
                // A surface container using the 'background' color from the theme
                Box(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    teamStateViewModel.doAction(TeamAction.IsTeamPresent)
                    SetBackGround( teamStateViewModel = teamStateViewModel)
                }
            }
        }
    }
}

@Composable
fun SetBackGround(teamStateViewModel: TeamStateViewModel) {
    val mContext = LocalContext.current
    Column(
        Modifier
            .background(Color.Black)
            .fillMaxSize()
    ) {
        val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.play_buttton_two))
        LottieAnimation(composition = composition)

        LaunchedEffect(Unit) {
            delay(7.seconds)
            if(teamStateViewModel.isTeamPresent)
                mContext.startActivity(Intent(mContext, MainActivity ::class.java))
            else
                mContext.startActivity(Intent(mContext,LoginActivity::class.java))
        }
    }
}
