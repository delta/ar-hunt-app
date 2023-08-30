package edu.nitt.delta.orientation22.compose.screens

import android.util.Log
import edu.nitt.delta.orientation22.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.PressGestureScope
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.ar.core.ArCoreApk
import edu.nitt.delta.orientation22.LandingButton
import edu.nitt.delta.orientation22.compose.*
import edu.nitt.delta.orientation22.compose.navigation.NavigationRoutes
import edu.nitt.delta.orientation22.di.viewModel.uiState.LoginState
import edu.nitt.delta.orientation22.ui.theme.*


@Composable
fun LoginScreen(
    painter: Painter,
    contentDescription: String,
    modifier: Modifier =Modifier,
    navController : NavController,
    state:LoginState,
    onSuccess:()->Unit,
    onFailure:()->String,
)
{
    val mContext= LocalContext.current
    if (state==LoginState.SUCCESS) onSuccess()
    else if (state==LoginState.ERROR) {
        val err=onFailure()
        Log.d("Login",err)
        mContext.toast(err)
        mContext.SnackShowSuccess(errorMessage = err, modifier = Modifier.fillMaxSize())
    }

    val configuration= LocalConfiguration.current
    val screenHeight=configuration.screenHeightDp
    val screenWidth=configuration.screenWidthDp

    val annotatedString = mContext.getAnnotatedString(lightGreen)
    val uriHandler = LocalUriHandler.current


    Box(
        modifier = Modifier
            .fillMaxSize()
    )
    {
        Image(
            painter = painterResource(id = R.drawable.landing1),
            contentDescription = "skull",
            modifier = Modifier
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
    }
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
                    .padding(10.dp, 0.dp, 0.dp, 0.dp),
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
        .fillMaxSize()
        .padding(top = (screenHeight / 3).dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ){
        Text(
            text = "A Pirate’s Pride",
            style = TextStyle(
                fontSize = 25.sp,
                fontFamily = FontFamily(Font(R.font.pirataone_regular)),
                fontWeight = FontWeight(400),
                color = white,
                textAlign = TextAlign.Center,
            )
        )
    }
    val onClick : PressGestureScope.(Offset) -> Unit = {
        if (ArCoreApk.getInstance().checkAvailability(mContext).isSupported) {
            if (state == LoginState.IDLE)
                navController.navigate(NavigationRoutes.DAuthWebView.route)
        }
        else {
            mContext.toast("Google AR Services is not supported. Please use a different device.")
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(state == LoginState.IDLE)
        {
            LandingButton(
                onClick = onClick,
                Content = {Text(
                    text = "Login With DAuth",
                    style = TextStyle(
                        fontSize = 25.sp,
                        color = black,
                        fontFamily = FontFamily(Font(R.font.daysone_regular)),
                    ),
                )}
            )
        }
        if (state == LoginState.LOADING){
            LandingButton(
                onClick = {  },
                Content = { LoadingIcon() }
            )
        }

        ClickableText(
            text = annotatedString,
            onClick = {
                annotatedString.getStringAnnotations("URL", it, it).firstOrNull()?.let { stringAnnotation ->
                    uriHandler.openUri(stringAnnotation.item)
                }
            },
            modifier = Modifier.padding(bottom = (screenHeight / 35).dp, top = (screenHeight / 30).dp),
        )
    }
}

