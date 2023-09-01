package edu.nitt.delta.orientation22

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.PressGestureScope
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import dagger.hilt.android.AndroidEntryPoint
import edu.nitt.delta.orientation22.compose.LoadingIcon
import edu.nitt.delta.orientation22.compose.getAnnotatedString
import edu.nitt.delta.orientation22.compose.screens.GameInfo
import edu.nitt.delta.orientation22.compose.toast
import edu.nitt.delta.orientation22.di.viewModel.actions.LoginAction
import edu.nitt.delta.orientation22.di.viewModel.actions.MapAction
import edu.nitt.delta.orientation22.di.viewModel.uiState.DownloadState
import edu.nitt.delta.orientation22.di.viewModel.uiState.LoginStateViewModel
import edu.nitt.delta.orientation22.di.viewModel.uiState.MapStateViewModel
import edu.nitt.delta.orientation22.ui.theme.*

@AndroidEntryPoint
class LiveActivity : ComponentActivity() {
    private val loginStateViewModel by viewModels<LoginStateViewModel>()
    private val mapStateViewModel by viewModels<MapStateViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginStateViewModel.doAction(LoginAction.IsLive)
        mapStateViewModel.doAction(MapAction.GetRoute)
        loginStateViewModel.doAction(LoginAction.IsDownloaded)

        setContent {
            Orientation22androidTheme {
                val context = LocalContext.current

                LiveScreen(
                    isLive = loginStateViewModel.isLive.value,
                    download = {
                        val routesData = mapStateViewModel.routeListData.value

                        val urls = routesData.map { it.glbUrl }
                        Log.d("Download", urls.toString())
                        context.toast("Please wait ...")

                        loginStateViewModel.doAction(LoginAction.DownloadAssets(urls, context))
                    },
                    downloadState = loginStateViewModel.downloadState,
                    isDownloaded = loginStateViewModel.isAssetsDownloaded,
                    error = loginStateViewModel.error,
                )
            }
        }
    }
}

@Composable
fun LiveScreen(
    isLive: Boolean,
    download: PressGestureScope.(Offset) -> Unit,
    downloadState: MutableState<DownloadState>,
    isDownloaded: Boolean,
    error: String?
) {

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    val screenWidth = configuration.screenWidthDp
    val annotatedString = LocalContext.current.getAnnotatedString(lightGreen)
    val uriHandler = LocalUriHandler.current
    val mContext = LocalContext.current
    val showDialog = remember { mutableStateOf(false) }

    val activity = mContext as Activity
    ActivityCompat.requestPermissions(
        activity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 23
    )

    if (downloadState.value == DownloadState.ERROR){
        Toast.makeText(mContext, error.toString(), Toast.LENGTH_SHORT).show()
        downloadState.value = DownloadState.IDLE
    }

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

        FilledIconButton(onClick = {
            showDialog.value = true
        },
            modifier= Modifier.size(50.dp).offset(20.dp, 20.dp),
            shape = CircleShape,
            colors = IconButtonDefaults.iconButtonColors(containerColor = red)
        ) {
            Icon(
                Icons.Default.Info,
                modifier = Modifier.scale(2f),
                contentDescription = "Info Icon",
                tint = black
            )
        }

        if (showDialog.value){
            GameInfo (
                showDialog = showDialog.value,
                onDismiss = { showDialog.value = false }
            )
        }
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

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(isLive)
        {
            if (isDownloaded || (downloadState.value == DownloadState.SUCCESS)){
                LandingButton(
                    onClick = {
                        val intent = Intent(mContext, MainActivity::class.java)
                        mContext.startActivity(intent)
                    },
                    Content = {Text(
                        text = "Play",
                        style = TextStyle(
                            fontSize = 30.sp,
                            color = black,
                            fontFamily = FontFamily(Font(R.font.daysone_regular)),
                        ),
                    )}
                )
            } else if (downloadState.value == DownloadState.IDLE){
                LandingButton(
                    onClick = download,
                    Content = {Text(
                        text = "Download Content",
                        style = TextStyle(
                            fontSize = 25.sp,
                            color = black,
                            fontFamily = FontFamily(Font(R.font.daysone_regular)),
                        ),
                    )}
                )
            } else if (downloadState.value == DownloadState.DOWNLOADING){
                LandingButton(
                    onClick = {  },
                    Content = { LoadingIcon() }
                )
            }
        }
        else
        {
            Text(text = "We'll be live soon!",style = TextStyle(
                fontSize = 30.sp,
                color = lightGrey,
                fontFamily = FontFamily(Font(R.font.daysone_regular)),
            ), modifier = Modifier.padding(bottom = (screenHeight / 15).dp)

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

@Composable
fun LandingButton(
    onClick: PressGestureScope.(Offset) -> Unit,
    Content: @Composable() () -> Unit
)
{
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    val screenWidth = configuration.screenWidthDp
    val brush = Brush.verticalGradient(listOf(lightGreen, lightCyan))

    Card(

        modifier= Modifier
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = onClick
                )
            }
            .height((screenHeight / 15).dp)
            .width((screenWidth * 0.8).dp)
        ,

        shape = RoundedCornerShape(25),
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
                Content()
            }
        }}
}

