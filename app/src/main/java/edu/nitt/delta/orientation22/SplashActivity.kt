package edu.nitt.delta.orientation22

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
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
import edu.nitt.delta.orientation22.ui.theme.Orientation22androidTheme
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds


@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Orientation22androidTheme {
                // A surface container using the 'background' color from the theme
                Box(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    SetBackGround()
                }
            }
        }
    }
}

@Composable
fun SetBackGround() {
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
            mContext.startActivity(Intent(mContext, MainActivity::class.java))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Orientation22androidTheme {
        SetBackGround()
    }
}