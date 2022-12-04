package edu.nitt.delta.orientation22.compose.screens

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import edu.nitt.delta.orientation22.MainActivity
import edu.nitt.delta.orientation22.R
import edu.nitt.delta.orientation22.compose.toast
import edu.nitt.delta.orientation22.models.game.LocationData
import edu.nitt.delta.orientation22.ui.theme.*
import io.github.sceneview.ar.ArSceneView

@Composable
fun ArScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    updateSceneView:(ArSceneView)->Unit,
    onClick : ()->Unit,
    currentLevel: Int,
    onResolve:()->Unit,
    routeList: List<LocationData>,
){
    AndroidView(factory = {
        context ->
        ArSceneView(
            context,
        ).apply {
            updateSceneView(this)
            Log.d("Resolve","About to resolve")
        }
    }
    )
    val mContext = LocalContext.current
    val isPopUp = remember { mutableStateOf(false) }
    Box(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            Row() {
                Button(
                    onClick = { isPopUp.value = true },
                    modifier = Modifier.padding(15.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = yellow, contentColor = black),
                    shape = RoundedCornerShape(5.dp)) {
                    Text(text = "Submit", fontFamily = FontFamily(Font(R.font.montserrat_regular)))
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                        onResolve()
                    },
                    modifier = Modifier.padding(15.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = yellow, contentColor = black),
                    shape = RoundedCornerShape(5.dp),
                ) {
                    Text(text = "Resolve", fontFamily = FontFamily(Font(R.font.montserrat_regular)))
                }
            }
        }
    }
    if (isPopUp.value) {
        Dialog(onDismissRequest = { isPopUp.value = false }){
            SubmitPopUp(isPopUp = isPopUp,onClick, currentLevel = currentLevel, routeList = routeList)
        }
    }
}

@Composable
fun SubmitPopUp(isPopUp : MutableState<Boolean>, onClick: () -> Unit, routeList: List<LocationData>, currentLevel: Int) {
    var text by remember { mutableStateOf("") }
    val mContext = LocalContext.current

    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(10.dp, 5.dp, 5.dp, 10.dp),
        elevation = 10.dp,
        ) {
        Column(
            Modifier.background(brown),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "SUBMIT THE CODE",
                fontFamily = FontFamily(Font(R.font.montserrat_regular)),
                color = brightYellow,
                modifier = Modifier.padding(10.dp, 5.dp, 5.dp, 5.dp),
                fontSize = 20.sp
            )
            TextField(
                value = text,
                onValueChange = { text = it },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = transparent,
                    disabledIndicatorColor = transparent,
                    unfocusedIndicatorColor = transparent,
                    cursorColor = brightYellow,
                    textColor = yellow
                ),
                shape = RoundedCornerShape(5.dp),
                textStyle = TextStyle.Default.copy(
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.montserrat_regular)),
                )
            )
            Text(
                "Hint: Check out the object to find the answer",
                fontFamily = FontFamily(Font(R.font.montserrat_regular)),
                color = peach,
                modifier = Modifier.padding(10.dp)
            )
            Row(
                modifier = Modifier.padding(all = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(5.dp),
                    onClick = {
                        text = ""
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = yellow)
                ) {
                    Text("Clear", fontFamily = FontFamily(Font(R.font.montserrat_regular)))
                }
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    onClick = {
                        Log.d("ans",currentLevel.toString())
                        routeList.forEach(
                        ){
                            if (it.position == currentLevel){
                                if (it.answer.equals(text, ignoreCase = true)){
                                    isPopUp.value = false
                                    mContext.toast("Congratulations!! You completed this level")
                                    onClick()
                                }
                                else{
                                    mContext.toast("Wrong code. Please check and try again!")
                                }
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = yellow)
                ) {
                    Text("Submit", fontFamily = FontFamily(Font(R.font.montserrat_regular)))
                }
            }
        }
    }
}

