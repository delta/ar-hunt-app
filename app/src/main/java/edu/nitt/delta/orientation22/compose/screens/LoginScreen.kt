package edu.nitt.delta.orientation22.compose.screens

import android.util.Log
import edu.nitt.delta.orientation22.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.ar.core.ArCoreApk
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
    val fontFamily= FontFamily(
        Font(R.font.montserrat_regular)
    )
    if (state==LoginState.SUCCESS) onSuccess()
    else if (state==LoginState.ERROR) {
        val err=onFailure()
        Log.d("Login",err)
        mContext.toast(err)
        mContext.SnackShowSuccess(errorMessage = err, modifier = Modifier.fillMaxSize())
    }

    val configuration= LocalConfiguration.current
    val screenHeight=configuration.screenHeightDp

    val annotatedString = mContext.getAnnotatedString(brightYellow)
    val uriHandler = LocalUriHandler.current
    Box(modifier = modifier.fillMaxSize())
    {
        Image(painter = painter, contentDescription = contentDescription,modifier=Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
        Column(modifier= Modifier
            .fillMaxSize()
            .background(translucentBackground), horizontalAlignment = Alignment.CenterHorizontally,) {
            Box(){Text(
                text= buildAnnotatedString {
                    withStyle(
                        style= SpanStyle(color = colour_Login_AR,
                            fontSize = 56.sp, fontFamily=fontFamily, fontWeight = FontWeight(600),
                        )
                    ){append("AR")}
                    append(" HUNT")}, style= TextStyle(fontSize = 48.sp, color = colour_Login_AR
                    , fontWeight = FontWeight(400), fontFamily = fontFamily, shadow = Shadow(color = shadow1,offset= Offset(0f,12f),
                        blurRadius = 3f
                    ), letterSpacing = 0.1.em


                ),modifier= Modifier
                    .padding(top = (screenHeight / 5).dp)
                    .blur(0.5.dp)
            )
                }
            Spacer(modifier = Modifier.height(120.dp))
            Button(modifier= Modifier
                .height(70.dp)
                .width(261.dp),colors= ButtonDefaults.buttonColors(containerColor
            = Color.hsl(0f,0f,0f,0.25f), contentColor = Color.hsl(47f,1f,0.61f,1f)
            ),onClick = {
                if (ArCoreApk.getInstance().checkAvailability(mContext).isSupported) {
                    if (state == LoginState.IDLE)
                        navController.navigate(NavigationRoutes.DAuthWebView.route)
                }
                else {
                    mContext.toast("Google AR Services is not supported. Please use a different device.")
                }
            }) {
                if(state==LoginState.IDLE)
                    Text(text = "LOGIN WITH \n DAUTH", fontSize = 20.sp, fontFamily = fontFamily,fontWeight = FontWeight(400), textAlign = TextAlign.Center, letterSpacing = 0.09.em, lineHeight = 24.sp)
                if(state==LoginState.LOADING)
                    LoadingIcon()
            }
            Spacer(modifier = Modifier.fillMaxSize(0.87f))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                ClickableText(
                    text = annotatedString,
                    onClick = {
                        annotatedString.getStringAnnotations("URL", it, it).firstOrNull()?.let { stringAnnotation ->
                            uriHandler.openUri(stringAnnotation.item)
                        }
                    }
                )
            }
        }
    }
}

