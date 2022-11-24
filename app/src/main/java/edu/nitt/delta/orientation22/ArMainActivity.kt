package edu.nitt.delta.orientation22

import android.annotation.SuppressLint
import android.content.Context
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.Dialog
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import edu.nitt.delta.orientation22.compose.ClueAlertBox
import edu.nitt.delta.orientation22.compose.toast
import edu.nitt.delta.orientation22.di.viewModel.actions.ArAction
import edu.nitt.delta.orientation22.di.viewModel.actions.MapAction
import edu.nitt.delta.orientation22.di.viewModel.uiState.ArStateViewModel
import edu.nitt.delta.orientation22.di.viewModel.uiState.MapStateViewModel
import edu.nitt.delta.orientation22.models.game.Location
import edu.nitt.delta.orientation22.models.game.LocationData
import edu.nitt.delta.orientation22.ui.theme.black
import edu.nitt.delta.orientation22.ui.theme.brown
import edu.nitt.delta.orientation22.ui.theme.yellow

@AndroidEntryPoint
class ArMainActivity : ComponentActivity() {
    companion object data {
        var glbUrl = "miyawaki.glb"
        var locationId = 0
        var anchorId = ""
    }
    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val arViewModel by viewModels<ArStateViewModel>()
        arViewModel.doAction(ArAction.FetchLocation)
        setContent {

            var locations = arViewModel.locationData.value

            val clipboardManager: ClipboardManager = LocalClipboardManager.current


            Log.d("location", locations.toString())
            val locationNames = mutableListOf<String>()
            locations.forEach() {
                locationNames.add(it.name)
            }

            var mSelectedText = remember { mutableStateOf("") }

            var cloudAnchorId = remember { mutableStateOf("") }

            clipboardManager.getText()?.text?.let {
                cloudAnchorId.value = it
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MyContent(locationNames, mSelectedText)
                Button(
                    onClick = {
                        if (mSelectedText.value == ""){
                            this@ArMainActivity.toast("Pick a location!")
                        }
                        else{
                        locations.forEach(){
                            if (it.name == mSelectedText.value){
                                arViewModel.locationId.value = it.id
                                arViewModel.glbUrl = it.glbUrl
                                glbUrl = it.glbUrl
                                locationId = it.id
                            }
                        }
                        Log.d("location-id", arViewModel.locationId.toString())
                        Log.d("glb-url1", glbUrl + mSelectedText.value)
                        Log.d("Locations", arViewModel.locationData.value.toString())
                        val intent = Intent(applicationContext, ArHostActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        intent.putExtra("LocationId", locationId)
                        startActivity(intent)
                    }},
                    modifier = Modifier.padding(10.dp)
                ){
                    Text("Host Anchor")
                }
                TextField(
                    value = cloudAnchorId.value,
                    onValueChange = { cloudAnchorId.value = it },
                    modifier = Modifier
                        .fillMaxWidth(0.8f).padding(20.dp),
                    label = {Text("Enter Cloud Anchor ID to resolve anchor")},
                )
                Button(
                    onClick = {
                        if (cloudAnchorId.value == ""){
                            this@ArMainActivity.toast("Cloud Anchor ID is required to resolve a cloud anchor")
                        }
                        else if (mSelectedText.value == ""){
                            this@ArMainActivity.toast("Pick a location!")
                        }
                        else {
                            anchorId = cloudAnchorId.value
                            locations.forEach() {
                                if (it.name == mSelectedText.value) {
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
                    }
                ){
                    Text("Resolve Anchor")
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

    Column(
        Modifier
            .padding(20.dp)
            .padding(top = 200.dp)
            .clickable { mExpanded.value = !mExpanded.value }) {

        TextField(
            value = mSelectedText.value,
            enabled = false,
            onValueChange = { mSelectedText.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    mTextFieldSize.value = coordinates.size.toSize()
                },
            label = {Text("Pick a Location to host/resolve it's corresponding AR Anchor")},
            trailingIcon = {
                Icon(icon,"contentDescription",
                    Modifier)
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