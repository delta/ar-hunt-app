package edu.nitt.delta.orientation22.compose.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.*
import com.google.android.gms.tasks.Task
import com.google.maps.android.compose.*
import edu.nitt.delta.orientation22.R
import edu.nitt.delta.orientation22.compose.CameraPermissionGetter
import edu.nitt.delta.orientation22.compose.ClueAlertBox
import edu.nitt.delta.orientation22.compose.openAr
import edu.nitt.delta.orientation22.constants.MapStyle
import edu.nitt.delta.orientation22.models.MarkerModel
import edu.nitt.delta.orientation22.ui.theme.*

@Composable
fun GoogleMapScreen(markerList: List<MarkerModel>) {
    val currentLocation = remember {
        mutableStateOf(LatLng(0.0, 0.0))
    }
    val locationReady = remember {
        mutableStateOf(false)
    }
    val mContext = LocalContext.current
    val fusedLocationProviderClient : FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(mContext)
    val location: Task<Location>
    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        location = fusedLocationProviderClient.lastLocation
        location.addOnSuccessListener {
            locationReady.value = true
            currentLocation.value = LatLng(it.latitude, it.longitude)
        }
    }

    val NITTLatLngBounds = LatLngBounds(
        LatLng(10.756930239262763, 78.80807559669776),  // SW bounds
        LatLng(10.768972299643412,  78.82329665044328) // NE bounds
    )
    val properties = MapProperties(mapType = MapType.NORMAL,
        isBuildingEnabled = true,
        minZoomPreference = 18.0f,
        isMyLocationEnabled = true,
        mapStyleOptions = MapStyleOptions(
            MapStyle.mapStyleJson),
        latLngBoundsForCameraTarget = NITTLatLngBounds)

    val uiSettings = MapUiSettings(
        compassEnabled = false,
        indoorLevelPickerEnabled = false,
        mapToolbarEnabled = false,
        myLocationButtonEnabled = true,
        zoomControlsEnabled = false,
        tiltGesturesEnabled = false,
        zoomGesturesEnabled = true)

    if (locationReady.value){
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.Builder().zoom(18.0f)
                .target(currentLocation.value)
                .tilt(90.0f)
                .build()
        }
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = properties,
            uiSettings = uiSettings,
            onMapLoaded = {
                Log.d("mapload","True")
            }
        ) {
            markerList.forEach { markers ->
                Marker(
                    state = markers.markerState,
                    title = markers.markerTitle,
                    snippet = markers.markerDescription,
                    flat = false,
                    icon = BitmapDescriptorFactory.fromResource(markers.markerImage),
                    visible = markers.isVisible
                )
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapScreen(
    markerList: List<MarkerModel>,
    currentClue: String,
    currentClueLocation: LatLng,
    currentglbUrl : String,
    currentanchorHash : String
){
    val mContext = LocalContext.current
    val showDialog = remember { mutableStateOf(false) }
    val fusedLocationProviderClient : FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(mContext)
    val permissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)
    CameraPermissionGetter(permissionState = permissionState)

    GoogleMapScreen(markerList = markerList)

    TopBar(mContext = mContext, fusedLocationProviderClient = fusedLocationProviderClient, showDialog = showDialog, currentClueLocation = currentClueLocation, permissionState = permissionState, currentClue = currentClue,currentglbUrl,currentanchorHash)
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun TopBar (
    mContext: Context,
    fusedLocationProviderClient: FusedLocationProviderClient,
    showDialog: MutableState<Boolean>,
    currentClueLocation: LatLng,
    permissionState: PermissionState,
    currentClue: String,
    currentglbUrl: String,
    currentanchorHash : String
){
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .padding(end = 50.dp)
    ) {
        Button(
            onClick = {
                openAr(permissionState, mContext, fusedLocationProviderClient, currentClueLocation,currentglbUrl,currentanchorHash)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = yellow
            )){
            Text(
                text = "AR Explore",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = black,
                fontFamily = FontFamily(Font(R.font.montserrat_regular))
            )
        }

        if (showDialog.value) {
            ClueAlertBox(clueName = "Current Clue",
                clueDescription = currentClue,
                showDialog = showDialog.value,
                onDismiss = {showDialog.value = false})
        }
        Button( onClick = {
            showDialog.value = true
        }, colors = ButtonDefaults.buttonColors(
            containerColor = yellow
        )){
            Text(
                text = "Current Clue",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = black,
                fontFamily = FontFamily(Font(R.font.montserrat_regular))
            )
        }
    }
}

