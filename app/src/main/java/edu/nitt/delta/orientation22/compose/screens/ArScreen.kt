package edu.nitt.delta.orientation22.compose.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import edu.nitt.delta.orientation22.R
import edu.nitt.delta.orientation22.ui.theme.black
import edu.nitt.delta.orientation22.ui.theme.transparent
import edu.nitt.delta.orientation22.ui.theme.white
import edu.nitt.delta.orientation22.ui.theme.yellow
import io.github.sceneview.ar.ArSceneView

@Composable
fun ArScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    updateSceneView:(ArSceneView)->Unit,
){
    AndroidView(factory = {
        context ->
        ArSceneView(
            context,
        ).apply {
            updateSceneView(this)
        }
    }
    )
    val isPopUp = remember { mutableStateOf(false) }
    Box(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            androidx.compose.material3.Button(onClick = {isPopUp.value = true}, modifier = Modifier.align(Alignment.Start)) {
                Text(text = "Submit")
            }
            androidx.compose.material3.Button(
                onClick = {
                          //call AR Reset
                },
                modifier = Modifier.align(alignment = Alignment.End)) {
                androidx.compose.material3.Text(text = "Reset")
            }
            Text(text = "In AR Screen")
        }
    }
    if (isPopUp.value) {
        Dialog(onDismissRequest = { isPopUp.value = false }){
            SubmitPopUp(isPopUp = isPopUp)
        }
    }
}

@Composable
fun SubmitPopUp(isPopUp : MutableState<Boolean>) {
    var text by remember { mutableStateOf("") }
    androidx.compose.material.Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(10.dp, 5.dp, 5.dp, 10.dp)
            .size(280.dp, 180.dp),
        elevation = 10.dp,

        ) {
        Column(Modifier.background(white), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Submit the Code", fontFamily = FontFamily(Font(R.font.montserrat_regular)))

            Column() {
                TextField(
                    value = text,
                    onValueChange = { text = it },
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = transparent,
                        disabledIndicatorColor = transparent,
                        unfocusedIndicatorColor = transparent,
                        cursorColor = black
                    )
                )
                Text("Hint: Check out the object to find the answer", fontFamily = FontFamily(Font(R.font.montserrat_regular)))
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
                        Text("Clear")
                    }
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        onClick = {
                            isPopUp.value = false
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = yellow)
                    ) {
                        Text("Submit")
                    }
                }
            }
        }
    }
}

