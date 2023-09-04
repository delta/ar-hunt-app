package edu.nitt.delta.orientation22

import android.content.Intent
import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.FloatTweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dagger.hilt.android.AndroidEntryPoint
import edu.nitt.delta.orientation22.compose.navigation.NavigationRoutes
import edu.nitt.delta.orientation22.compose.toast
import edu.nitt.delta.orientation22.di.viewModel.actions.LoginAction
import edu.nitt.delta.orientation22.di.viewModel.actions.MapAction
import edu.nitt.delta.orientation22.di.viewModel.uiState.LoginState
import edu.nitt.delta.orientation22.di.viewModel.uiState.LoginStateViewModel
import edu.nitt.delta.orientation22.di.viewModel.uiState.MapStateViewModel
import edu.nitt.delta.orientation22.ui.theme.Orientation22androidTheme
import edu.nitt.delta.orientation22.ui.theme.black
import edu.nitt.delta.orientation22.ui.theme.cyan
import edu.nitt.delta.orientation22.ui.theme.white


@AndroidEntryPoint
class SplashActivity : ComponentActivity() {
    private val loginStateViewModel by viewModels<LoginStateViewModel>()
    private val mapStateViewModel by viewModels<MapStateViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginStateViewModel.doAction(LoginAction.IsLoggedIn)
        loginStateViewModel.doAction(LoginAction.IsLive)
        mapStateViewModel.doAction(MapAction.GetRoute)
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
fun ProgressBar(
    modifier: Modifier = Modifier,
    progress: Float,
    progressColor: Color = cyan,
    backgroundColor: Color = black,
) {
    Column() {
        Box(
            modifier = modifier
                .background(color = progressColor, shape = RoundedCornerShape(8.dp))
                .padding(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(backgroundColor, shape = RoundedCornerShape(4.dp))
                    .padding(4.dp)
            ) {
                Box(
                    modifier = modifier
                        .background(backgroundColor, shape = RoundedCornerShape(4.dp))
                        .height(30.dp)
                        .fillMaxWidth(0.7f)
                ) {
                    Box(
                        modifier = Modifier
                            .background(progressColor, shape = RoundedCornerShape(4.dp))
                            .fillMaxHeight()
                            .fillMaxWidth(progress)
                    )
                }
            }
        }
        Text(
            text = "LOADING ...",
            style = TextStyle(
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.daysone_regular)),
                color = white,
                textAlign = TextAlign.Left,
            ),
            modifier = Modifier.padding(4.dp)
        )
    }
}

@Composable
fun SplashContent(
    scale: Float,
    progress: Float
){
    Column(

        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "AR",
                    style = TextStyle(
                        fontSize = 45.sp,
                        color = white,
                        fontFamily = FontFamily(Font(R.font.daysone_regular)),
                    ),
                )
                Image(
                    painter = painterResource(id = R.drawable.ar_hunt_logo),
                    contentDescription = "AR Hunt Logo",
                    modifier = Modifier
                        .size(70.dp, 60.dp)
                        .padding(10.dp, 0.dp, 0.dp, 0.dp)
                )
            }
            Text(
                text = "HUNT",
                style = TextStyle(
                    fontSize = 45.sp,
                    color = white,
                    fontFamily = FontFamily(Font(R.font.daysone_regular))
                )
            )
        }
        Image(
            painter = painterResource(id = R.drawable.splash_skull),
            contentDescription = "Splash Skull",
            modifier = Modifier.scale(scale)
        )

        ProgressBar(progress = progress)
    }
}
@Composable
fun SetBackGround(
    loginStateViewModel: LoginStateViewModel,
    onCompleted:() ->Unit
) {
    val mContext = LocalContext.current
    val scale = remember {
        Animatable(0f)
    }
    val progress = remember {
        Animatable(0f)
    }
    val hesitateEasing = CubicBezierEasing(0f, 1f, 1f, 0f)
    LaunchedEffect(key1 = true){
        scale.animateTo(
            targetValue = 2.5f,
            animationSpec = tween(
                durationMillis = 500,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                }
            )
        )
        progress.animateTo(
            targetValue = 1f,
            animationSpec = FloatTweenSpec(1000, 0, hesitateEasing)
        )
        if (loginStateViewModel.isLoggedIn) {
            loginStateViewModel.doAction(LoginAction.IsRegistered)
            if (loginStateViewModel.uiState.value == LoginState.ERROR){
                loginStateViewModel.uiState.value = LoginState.IDLE
                mContext.toast("Connect to the internet and reload the app.")
                val intent = Intent(mContext,LiveActivity::class.java)
                mContext.startActivity(intent)
            } else if (loginStateViewModel.isRegistered.value.isRegistered) {
                    val intent = Intent(mContext,LiveActivity::class.java)
                    mContext.startActivity(intent)
            } else {
                LoginActivity.startDestination = NavigationRoutes.TeamDetails.route
                mContext.startActivity(Intent(mContext, LoginActivity::class.java))
            }
        } else{
            LoginActivity.startDestination = NavigationRoutes.Login.route
            mContext.startActivity(Intent(mContext,LoginActivity::class.java))
        }
    }
    SplashContent(scale = scale.value, progress = progress.value)
}