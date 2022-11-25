package edu.nitt.delta.orientation22

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.google.android.filament.utils.Utils
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Orientation22androidTheme {
                liveScreen()
            }
        }
    }
}

@Composable
fun liveScreen()
{
    val painter= painterResource(id = R.drawable.background_image)
    val mContext = LocalContext.current
    val configuration= LocalConfiguration.current
    val screenHeight=configuration.screenHeightDp
    val annotatedString = mContext.getAnnotatedString(brightYellow)
    val uriHandler = LocalUriHandler.current
    val fontFamily= FontFamily(
        Font(R.font.montserrat_regular)
    )
    Box(modifier = Modifier.fillMaxSize())
    {
        Image(painter = painter, contentDescription = "LiveScreen",modifier= Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
        Column(modifier= Modifier
            .fillMaxSize()
            .background(translucentBackground), horizontalAlignment = Alignment.CenterHorizontally,) {
            Box(){
                Text(
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
            Box(){
                Text(text = " WILL BE LIVE SOON", style =TextStyle(fontSize = 27.sp, color = colour_Login_AR
                    , fontWeight = FontWeight(400), fontFamily = fontFamily, shadow = Shadow(color = shadow1,offset= Offset(0f,12f),
                        blurRadius = 3f
                    ), letterSpacing = 0.1.em
                ),modifier= Modifier
                    .padding(top = 10.dp)
                    .blur(0.5.dp) )

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

