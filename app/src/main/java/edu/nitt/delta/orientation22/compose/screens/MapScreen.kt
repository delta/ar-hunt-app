package edu.nitt.delta.orientation22.compose.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
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
import edu.nitt.delta.orientation22.compose.toast
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
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val mContext = LocalContext.current
    val fusedLocationProviderClient : FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(mContext)
    val location: Task<Location>
    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        location = fusedLocationProviderClient.lastLocation
        location.addOnSuccessListener {
            try {
                locationReady.value = true
                currentLocation.value = LatLng(it.latitude, it.longitude)
            } catch (e:Exception){
                currentLocation.value = LatLng(10.7589,78.8132)
                mContext.toast("Turn On Location Service")
            }
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
            modifier = Modifier
                .fillMaxSize()
                .padding(top = (screenHeight / 14).dp),
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
    currentglbUrl: String,
    currentanchorHash: String,
    currentScale: Double,
    currentLevel: Int,
    currentPoints: Int,
){
    val mContext = LocalContext.current
    val showDialog = remember { mutableStateOf(false) }
    val fusedLocationProviderClient : FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(mContext)
    val permissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)
    CameraPermissionGetter(permissionState = permissionState)

    TopBar(mContext = mContext, fusedLocationProviderClient = fusedLocationProviderClient, showDialog = showDialog, currentClueLocation = currentClueLocation, permissionState = permissionState, currentClue = currentClue,currentglbUrl,currentanchorHash,currentScale,currentLevel, currentPoints)
    GoogleMapScreen(markerList = markerList)
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
    currentanchorHash: String,
    currentScale: Double,
    currentLevel: Int,
    currentPoints: Int
){
    val screenHeight = LocalConfiguration.current.screenHeightDp
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .height((screenHeight / 14).dp).padding(top = (screenHeight / 224).dp),
        verticalAlignment = Alignment.Top
    ) {

        Button(
            onClick = {
                showDialog.value = true
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = transparent
            ),
            shape = RoundedCornerShape(25),
            modifier = Modifier.clip(RoundedCornerShape(25))
                .background(Brush.linearGradient(
                    0.0f to yellowStart,
                    1.0f to brightYellow,
                    start = Offset.Zero,
                    end = Offset.Infinite
                )).height((screenHeight/16).dp)
        ){
                Icon(
                    painter = painterResource(id = R.drawable.clue),
                    contentDescription = "Hint Symbol",
                    modifier = Modifier.scale(2.5f),
                    tint = Color.Unspecified
                )
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = "HINT",
                    fontSize = 15.sp,
                    color = black,
                    fontFamily = FontFamily(Font(R.font.daysone_regular))
                )
            }


        Button(
            onClick = {
                if (currentLevel > 5){
                    mContext.toast("Congratulations, you have completed the AR Hunt!")
                }
                else {
                    openAr(
                        permissionState,
                        mContext,
                        fusedLocationProviderClient,
                        currentClueLocation,
                        currentglbUrl,
                        currentanchorHash,
                        currentScale
                    )
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = transparent
            ),
            shape = RoundedCornerShape(25),
            modifier = Modifier.clip(RoundedCornerShape(25))
                .background(grey).height((screenHeight/16).dp)
            ){
            Icon(
                painter = painterResource(id = R.drawable.camera),
                contentDescription = "Explore Symbol",
                modifier = Modifier.scale(1.5f),
                tint = white
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = "EXPLORE",
                fontSize = 14.sp,
                color = white,
                fontFamily = FontFamily(Font(R.font.daysone_regular))
            )

            Box(
                modifier = Modifier,
            ){

            }
        }

        if (showDialog.value) {
            if (currentLevel > 5){
                ClueAlertBox(clueName = "Congratulations",
                    clueDescription = "You have completed the AR Hunt. Visit the leaderboard page to check your team score. We hope you enjoyed the game!",
                    showDialog = showDialog.value,
                    onDismiss = { showDialog.value = false })
            }
            else {
                ClueAlertBox(clueName = "Current Clue",
                    clueDescription = currentClue,
                    showDialog = showDialog.value,
                    onDismiss = { showDialog.value = false })
            }
        }

        Points(currentPoints)
    }
}

@Composable
fun Points(
    currentPoints: Int
){
    val screenHeight = LocalConfiguration.current.screenHeightDp
    Box(
        modifier = Modifier.clip(RoundedCornerShape(25))
            .background(grey)
            .fillMaxWidth(0.8f).height((screenHeight/16).dp)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(end = 20.dp).align(Alignment.Center),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = currentPoints.toString(),
                modifier = Modifier.padding(end = 10.dp, start = 15.dp),
                style = TextStyle(
                    fontSize = 13.sp,
                    fontFamily = FontFamily(Font(R.font.daysone_regular)),
                    color = white,
                ),
            )
            Icon(
                modifier = Modifier.scale(2f),
                painter = painterResource(id = R.drawable.coins),
                contentDescription = "Coins Icon",
                tint = Color.Unspecified
            )
        }
    }
}

