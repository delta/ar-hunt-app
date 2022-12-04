package edu.nitt.delta.orientation22

import android.app.Notification.Action
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import dagger.hilt.android.AndroidEntryPoint
import edu.nitt.delta.orientation22.compose.navigation.NavigationRoutes
import edu.nitt.delta.orientation22.di.viewModel.actions.LoginAction
import edu.nitt.delta.orientation22.di.viewModel.uiState.LoginStateViewModel
import edu.nitt.delta.orientation22.ui.theme.Orientation22androidTheme
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds


@AndroidEntryPoint
class SplashActivity : ComponentActivity() {
    private val loginStateViewModel by viewModels<LoginStateViewModel> ()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginStateViewModel.doAction(LoginAction.IsLoggedIn)
        loginStateViewModel.doAction(LoginAction.IsLive)
        setContent {
            Orientation22androidTheme {
                // A surface container using the 'background' color from the theme
                Box(
                    modifier = Modifier.fillMaxSize(),
                ) {

                    SetBackGround(loginStateViewModel){
                        finish()
                    }
                }
            }
        }
    }
}

@Composable
fun SetBackGround(
    loginStateViewModel: LoginStateViewModel,
    onCompleted:() ->Unit
    ) {
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
            if (loginStateViewModel.isLoggedIn) {
                    if (loginStateViewModel.isRegistered.value.isRegistered) {
                        if(loginStateViewModel.isLive.value) {
                            mContext.startActivity(Intent(mContext, MainActivity::class.java))
                        }
                        else {
                            val intent = Intent(mContext,LiveActivity::class.java)
                            mContext.startActivity(intent)
                        }
                    } else {
                        LoginActivity.startDestination = NavigationRoutes.TeamDetails.route
                        mContext.startActivity(Intent(mContext, LoginActivity::class.java))
                    }
            } else{
                LoginActivity.startDestination = NavigationRoutes.Login.route
                mContext.startActivity(Intent(mContext,LoginActivity::class.java))
            }
        }
    }
}
