package edu.nitt.delta.orientation22

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import dagger.hilt.android.AndroidEntryPoint
import edu.nitt.delta.orientation22.di.viewModel.actions.ArAction
import edu.nitt.delta.orientation22.di.viewModel.actions.MapAction
import edu.nitt.delta.orientation22.di.viewModel.uiState.ArStateViewModel
import edu.nitt.delta.orientation22.di.viewModel.uiState.MapStateViewModel
import edu.nitt.delta.orientation22.ui.theme.brown

@AndroidEntryPoint
class ArMainActivity : ComponentActivity() {
    companion object data {
        var glbUrl = "miyawaki.glb"
        var locationId = 0
    }
    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val arViewModel by viewModels<ArStateViewModel>()
        arViewModel.doAction(ArAction.FetchLocation)
        setContent {

            var locations = arViewModel.locationData.value

            Log.d("location", locations.toString())
            val locationNames = mutableListOf<String>()
            locations.forEach() {
                locationNames.add(it.name)
            }

            var mSelectedText = remember { mutableStateOf("") }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MyContent(locationNames, mSelectedText)
                Button(
                    onClick = {
                        locations.forEach(){
                            if (it.name == mSelectedText.value){
                                arViewModel.locationId.value = it.id
                                arViewModel.glbUrl = it.glbUrl
                                glbUrl = it.glbUrl
                                locationId = it.id
                            }
                        }
                        Log.d("location-id", arViewModel.locationId.toString())
                        Log.d("glb-url", arViewModel.glbUrl)
                        Log.d("Locations", arViewModel.locationData.value.toString())
                        val intent = Intent(applicationContext, ArHostActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        intent.putExtra("LocationId", locationId)
                        startActivity(intent)
                    }
                ){
                    Text("Host")
                }
                Button(
                    onClick = {
                        locations.forEach(){
                            if (it.name == mSelectedText.value){
                                arViewModel.locationId.value = it.id
                                arViewModel.glbUrl = it.glbUrl
                                glbUrl = it.glbUrl
                                locationId = it.id
                            }
                        }
                        val intent = Intent(applicationContext, ArResolveActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }
                ){
                    Text("Resolve")
                }
            }
        }
    }
}

@Composable
fun MyContent(locationNames: MutableList<String>, mSelectedText: MutableState<String>) {
    var mExpanded = remember { mutableStateOf(false) }


    var mTextFieldSize = remember { mutableStateOf(Size.Zero)}
    val icon = if (mExpanded.value)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(Modifier.padding(20.dp)) {
        TextField(
            value = mSelectedText.value,
            enabled = false,
            onValueChange = { mSelectedText.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    mTextFieldSize.value = coordinates.size.toSize()
                },
            label = {Text("Location")},
            trailingIcon = {
                Icon(icon,"contentDescription",
                    Modifier.clickable { mExpanded.value = !mExpanded.value })
            },
            colors = TextFieldDefaults.textFieldColors(disabledTextColor = brown)
        )
        DropdownMenu(
            expanded = mExpanded.value,
            onDismissRequest = { mExpanded.value = false },
            modifier = Modifier
                .width(with(LocalDensity.current){mTextFieldSize.value.width.toDp()})
        ) {
            locationNames.forEach { label ->
                DropdownMenuItem(onClick = {
                    mSelectedText.value = label
                    mExpanded.value = false
                }) {
                    Text(text = label)
                }
            }
        }
    }
}