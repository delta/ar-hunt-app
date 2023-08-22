package edu.nitt.delta.orientation22

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.android.filament.utils.Utils
import com.google.ar.sceneform.rendering.ViewRenderable.HorizontalAlignment
import dagger.hilt.android.AndroidEntryPoint
import edu.nitt.delta.orientation22.compose.LoadingIcon
import edu.nitt.delta.orientation22.compose.getAnnotatedString
import edu.nitt.delta.orientation22.compose.navigation.NavigationOuter
import edu.nitt.delta.orientation22.compose.navigation.NavigationRoutes
import edu.nitt.delta.orientation22.di.viewModel.uiState.LoginState
import edu.nitt.delta.orientation22.di.viewModel.uiState.LoginStateViewModel
import edu.nitt.delta.orientation22.di.viewModel.uiState.TeamStateViewModel
import edu.nitt.delta.orientation22.ui.theme.*
import okhttp3.internal.applyConnectionSpec

@AndroidEntryPoint
class LiveActivity : ComponentActivity() {
    private val loginStateViewModel by viewModels<LoginStateViewModel> ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Orientation22androidTheme {
                liveScreen(loginStateViewModel)
            }
        }
    }
}

@Composable
fun liveScreen(
    loginStateViewModel: LoginStateViewModel,

) {
    val boat= painterResource(id = R.drawable.sailboat)
    val skull = painterResource(id = R.drawable.skull)
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    val screenWidth = configuration.screenWidthDp

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)

    )
    {
                Image(
                    painter = skull,
                    contentDescription = "skull",
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
        }
        Column(modifier= Modifier
            .fillMaxSize()
            .background(translucentBackground), horizontalAlignment = Alignment.CenterHorizontally,) {}
    Column(

        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    )
    {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "AR",
                style = TextStyle(
                    fontSize = 63.sp,
                    color = white,
                    fontFamily = FontFamily(Font(R.font.daysone_regular)),
                ),
            )
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "AR Hunt Logo",
                modifier = Modifier
                    .size((screenWidth / 4).dp, 70.dp)
                    .padding(10.dp, 0.dp, 0.dp, 0.dp)
            )
        }

        Text(
            text = "HUNT",
            style = TextStyle(
                fontSize = 63.sp,
                color = white,
                fontFamily = FontFamily(Font(R.font.daysone_regular))
            ),

            )
    }
    Column(modifier = Modifier
        .fillMaxSize(),
        verticalArrangement = Arrangement.Center

    ){
        Image(painter= boat, contentDescription = "logo", modifier = Modifier
            .size(screenWidth.dp, (screenHeight / 2).dp)
            .padding(top = (screenHeight / 50).dp))
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(top=(screenHeight/3).dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ){
        Text(
            text = "A Pirate’s Pride",
            style = TextStyle(
                fontSize = 38.sp,
                fontFamily = FontFamily(Font(R.font.pirataone_regular)),
                fontWeight = FontWeight(400),
                color = white,
                textAlign = TextAlign.Center,
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = (screenHeight / 10).dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(loginStateViewModel.isLive.value)
        {
            Button()
        }
        else
        {
            Text(text = "Will be live Soon !",style = TextStyle(
                fontSize = 34.sp,
                color = peach_version2,
                fontFamily = FontFamily(Font(R.font.daysone_regular)),
            ),)
        }
    }
    }

@Composable
fun Button()
{
    val mContext = LocalContext.current
    val configuration = LocalConfiguration.current
    val screenHeight=configuration.screenHeightDp
    val brush = Brush.linearGradient(listOf(peach_version2, pink))
    Card(

        modifier= Modifier
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        val intent = Intent(mContext, LoginActivity::class.java)
                        mContext.startActivity(intent)
                    }
                )
            }
            .fillMaxWidth()
            .height((screenHeight / 10).dp)
            .padding((screenHeight / 60).dp)
            ,

        shape = RoundedCornerShape(4.dp),
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush)
        ){
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Play",
                style = TextStyle(
                    fontSize = 38.sp,
                    color = white,
                    fontFamily = FontFamily(Font(R.font.daysone_regular)),
                ),
            )
        }
    }}
}


