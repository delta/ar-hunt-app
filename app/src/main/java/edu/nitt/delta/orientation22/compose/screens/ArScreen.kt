package edu.nitt.delta.orientation22.compose.screens

import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import edu.nitt.delta.orientation22.R
import edu.nitt.delta.orientation22.compose.SpeedDialData
import edu.nitt.delta.orientation22.compose.SpeedDialFloatingActionButton
import edu.nitt.delta.orientation22.compose.toast
import edu.nitt.delta.orientation22.di.api.ApiInterface
import edu.nitt.delta.orientation22.di.api.ApiRoutes
import edu.nitt.delta.orientation22.models.game.CheckAnswerRequest
import edu.nitt.delta.orientation22.ui.theme.*
import io.github.sceneview.ar.ArSceneView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun ArScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    updateSceneView:(ArSceneView)->Unit,
    onClick : ()->Unit,
    onReset:()->Unit,
    onBack: () -> Unit,
    answer: String,
){

    val isPopUp = remember { mutableStateOf(false) }
    val isCorrect = remember { mutableStateOf(false) }
    BackHandler(onBack = onBack)
    Scaffold(
        modifier = modifier,
        content = {
                  paddingValues ->
            AndroidView(
                modifier = Modifier.padding(paddingValues),
                factory = {
                    context ->
                ArSceneView(
                    context,
                ).apply {
                    updateSceneView(this)
                    Log.d("Resolve","About to resolve")
                }
            }
            )
        },
        floatingActionButton = {
            SpeedDialFloatingActionButton(
                modifier = Modifier,
                initialExpanded = false,
                animationDuration = 300,
                animationDelayPerSelection = 100,
                showLabels = true,
                fabBackgroundColor = black,
                fabContentColor = white,
                speedDialBackgroundColor = brightYellow,
                speedDialContentColor = black,
                speedDialData = listOf(
                    SpeedDialData(
                        label = "SUBMIT",
                        painterResource = R.drawable.treasure,
                        scale = 0.21f
                    ) {
                        isPopUp.value = true
                    },
                    SpeedDialData(
                        label = "RESET AR",
                        painterResource = R.drawable.reset,
                        scale = 1f
                    ) {
                        onReset()
                    },
                )
            )
        },
        floatingActionButtonPosition = FabPosition.End
    )
    if (isPopUp.value) {
        Dialog(onDismissRequest = { isPopUp.value = false }){
            SubmitPopUp(isPopUp = isPopUp, onClick, answer = answer, isCorrect = isCorrect, onBack = onBack)
        }
    }
}

@Composable
fun SubmitPopUp(isPopUp : MutableState<Boolean>, onClick: () -> Unit, answer: String, isCorrect: MutableState<Boolean>, onBack: () -> Unit) {
    var text by remember { mutableStateOf("") }
    val mContext = LocalContext.current
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val screenWidth = LocalConfiguration.current.screenWidthDp

    Dialog(onDismissRequest = { isPopUp.value = false }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(5))
                .paint(
                    painter = painterResource(id = R.drawable.dialog_background),
                    contentScale = ContentScale.FillBounds
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height((screenHeight / 40).dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.treasure_big),
                    contentDescription = "Treasure Big",
                    modifier = Modifier
                        .height((screenHeight / 12).dp),
                    contentScale = ContentScale.FillHeight
                )
                Text(
                    text = "SECRET",
                    fontSize = 45.sp,
                    fontWeight = FontWeight.Normal,
                    color = white,
                    fontFamily = FontFamily(Font(R.font.fiddlerscove)),
                    textAlign = TextAlign.Center,
                )
            }

            Spacer(modifier = Modifier.height((screenHeight / 30).dp))

            OtpTextField(
                otpText = text,
                onOtpTextChange = { value, _ ->
                    text = value
                },
                otpCount = answer.length
            )

            Spacer(modifier = Modifier.height((screenHeight / 40).dp))
            TextButton(
                onClick = {
                    val retrofit= Retrofit.Builder()
                        .client(OkHttpClient())
                        .baseUrl(ApiRoutes.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                    val apiInterface = retrofit.create(ApiInterface::class.java)
                    CoroutineScope(Dispatchers.IO).launch {
                        val response = apiInterface.checkAnswer(CheckAnswerRequest(text))
                        if(response.message=="correct"){
                            isPopUp.value = false
                            mContext.toast("Congratulations! You've completed this level.")
                            if (!isCorrect.value){
                                onClick()
                                isCorrect.value = true
                            }
                            onBack()
                        }
                        else{
                            mContext.toast("Wrong code. Please try again!")
                        }
                    }
                },
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = cyan)
            ) {
                Text(
                    text = "CRACK",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = black,
                    fontFamily = FontFamily(Font(R.font.fiddlerscove))
                )
            }
            Spacer(modifier = Modifier.height((screenHeight / 40).dp))
        }
    }
}

@Composable
fun OtpTextField(
    modifier: Modifier = Modifier,
    otpText: String,
    otpCount: Int,
    onOtpTextChange: (String, Boolean) -> Unit
) {
    LaunchedEffect(Unit) {
        if (otpText.length > otpCount) {
            throw IllegalArgumentException("Otp text value must not have more than otpCount: $otpCount characters")
        }
    }

    val half : Int = otpCount / 2

    BasicTextField(
        modifier = modifier.fillMaxWidth(),
        value = TextFieldValue(otpText, selection = TextRange(otpText.length)),
        onValueChange = {
            if (it.text.length <= otpCount) {
                onOtpTextChange.invoke(it.text, it.text.length == otpCount)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        decorationBox = {

            if (otpCount <= 5) {
                Row(horizontalArrangement = Arrangement.Center) {
                    repeat(otpCount) { index ->
                        CharView(
                            index = index,
                            text = otpText
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(horizontalArrangement = Arrangement.Center) {
                        repeat(half) { index ->
                            CharView(
                                index = index,
                                text = otpText
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.Center) {
                        repeat(otpCount - half) { index ->
                            CharView(
                                index = index + half,
                                text = otpText
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                    }
                }
            }
        }
    )
}

@Composable
private fun CharView(
    index: Int,
    text: String
) {
    val isFocused = text.length == index
    val char = when {
        index >= text.length -> ""
        else -> text[index].toString()
    }
    Text(
        modifier = Modifier
            .width(40.dp)
            .border(
                1.dp, when {
                    isFocused -> white
                    else -> grey
                }, RoundedCornerShape(8.dp)
            )
            .padding(2.dp)
            .background(black),
        text = char,
        style = TextStyle(
            fontSize = 35.sp
        ),
        color = white,
        textAlign = TextAlign.Center,
        fontFamily = FontFamily(Font(R.font.fiddlerscove)),
    )
}

@Composable
fun BackHandler(enabled: Boolean = true, onBack: () -> Unit) {
    val currentOnBack by rememberUpdatedState(onBack)
    val backCallback = remember {
        object : OnBackPressedCallback(enabled) {
            override fun handleOnBackPressed() {
                currentOnBack()
            }
        }
    }
    SideEffect {
        backCallback.isEnabled = enabled
    }
    val backDispatcher = checkNotNull(LocalOnBackPressedDispatcherOwner.current) {
        "No OnBackPressedDispatcherOwner was provided via LocalOnBackPressedDispatcherOwner"
    }.onBackPressedDispatcher
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner, backDispatcher) {
        backDispatcher.addCallback(lifecycleOwner, backCallback)
        onDispose {
            backCallback.remove()
        }
    }
}

