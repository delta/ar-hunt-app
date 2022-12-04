package edu.nitt.delta.orientation22.compose

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.PermissionState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import edu.nitt.delta.orientation22.ArActivity
import edu.nitt.delta.orientation22.compose.navigation.NavigationRoutes
import edu.nitt.delta.orientation22.ui.theme.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import edu.nitt.delta.orientation22.R
import kotlinx.coroutines.launch

fun Context.toast(message: CharSequence)
{
Toast.makeText(this,message,Toast.LENGTH_LONG).show()
}

@OptIn(ExperimentalPermissionsApi::class)
fun locationPermissionCheck(
    navController: NavController,
    permissionState: MultiplePermissionsState,
    mContext: Context,
) {
    permissionState.permissions.forEach { perm ->
        when(perm.permission){
            Manifest.permission.ACCESS_FINE_LOCATION -> {
                when {
                    perm.hasPermission -> {
                        navController.navigate(NavigationRoutes.Map.route) {
                            launchSingleTop = true
                            popUpTo(NavigationRoutes.Dashboard.route)
                        }
                    }
                    perm.shouldShowRationale -> {
                        mContext.toast("Fine Location Access is required to open Map Screen.")
                    }
                    !perm.hasPermission && !perm.shouldShowRationale -> {
                        mContext.toast("Enable Fine Location Access in the settings to open Map Screen.")
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        val uri: Uri = Uri.fromParts("package", "edu.nitt.delta.orientation22", null)
                        intent.data = uri
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                        mContext.startActivity(intent)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
fun openAr(
    permissionState: PermissionState,
    mContext: Context,
    fusedLocationProviderClient: FusedLocationProviderClient,
    currentClueLocation: LatLng,
    glbUrl: String,
    anchorHash: String,
    currentScale: Double,
) {
    when{
        permissionState.hasPermission -> {
            distanceCalculator(fusedLocationProviderClient, mContext, currentClueLocation,glbUrl,anchorHash,currentScale)
        }
        permissionState.shouldShowRationale -> {
            mContext.toast("Camera Access is required for AR Explore.")
        }
        !permissionState.hasPermission && !permissionState.shouldShowRationale -> {
            mContext.toast("Enable Camera Access in the settings to open Map Screen.")
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri: Uri = Uri.fromParts("package", "edu.nitt.delta.orientation22", null)
            intent.data = uri
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
            mContext.startActivity(intent)
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationPermissionGetter(
    permissionState: MultiplePermissionsState,
){
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner,
        effect = {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_RESUME){
                    permissionState.launchMultiplePermissionRequest()
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)
            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }
    )
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraPermissionGetter(
    permissionState: PermissionState,
){
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner,
        effect = {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_RESUME){
                    permissionState.launchPermissionRequest()
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)
            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }
    )
}


@Composable
fun ClueAlertBox(clueName: String,
          clueDescription: String,
          showDialog: Boolean,
          onDismiss: () -> Unit) {
    if (showDialog) {
        AlertDialog(
            containerColor = brown,
            title = {
                Box(
                ) {
                    Text(
                        text = clueName,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = brightYellow,
                        fontFamily = FontFamily(Font(R.font.montserrat_regular)),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            },
            text = {
                Text(
                    text = clueDescription,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    color = peach,
                    fontFamily = FontFamily(Font(R.font.montserrat_regular)),
                    textAlign = TextAlign.Center,
                )
            },
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton( onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = yellow)
                ) {
                    Text(
                        text = "OK",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = black,
                        fontFamily = FontFamily(Font(R.font.montserrat_regular))
                    )
                }
            },
            dismissButton = {
            }
        )
    }
}

fun distanceCalculator(
    fusedLocationProviderClient: FusedLocationProviderClient,
    mContext: Context,
    currentClueLocation: LatLng,
    glbUrl: String,
    anchorHash: String,
    currentScale: Double
){
    val radius = 10
    if (
        ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    ) {
        val location = fusedLocationProviderClient.lastLocation
        location.addOnSuccessListener {
            try {
                val results = FloatArray(1)
                Location.distanceBetween(
                    it.latitude,
                    it.longitude,
                    currentClueLocation.latitude,
                    currentClueLocation.longitude,
                    results
                )
                val distanceInMeters = results[0]
//                mContext.toast(distanceInMeters.toString())
                if (distanceInMeters <= radius) {
                    val intent = Intent(mContext, ArActivity::class.java)
                    intent.putExtra("glb", glbUrl)
                    intent.putExtra("anchorHash", anchorHash)
                    intent.putExtra("anchorScale", currentScale)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    mContext.startActivity(intent)
                } else {
                    mContext.toast("You are too far from your current clue.")
                }
            } catch (e:Exception){
                mContext.toast("Turn On Location Service")
            }
        }
    }
}

fun Context.getAnnotatedString(color: Color) : AnnotatedString {
    val annotatedString = buildAnnotatedString {
        val str = "MADE WITH ❤ BY DELTA FORCE AND ORIENTATION"
        val indexStartDelta = str.indexOf("DELTA FORCE")
        val indexStartOrientation = str.indexOf("ORIENTATION")
        append(str)
        addStyle(
            style = SpanStyle(
                color = color,
                fontFamily = FontFamily(Font(R.font.montserrat_regular)),
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp
            ),
            start = 0,
            end = 42
        )
        addStyle(
            style = SpanStyle(
                color = color,
                fontFamily = FontFamily(Font(R.font.montserrat_regular)),
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp

            ),
            start = indexStartDelta,
            end = indexStartDelta+11
        )
        addStyle(
            style = SpanStyle(
                color = color,
                fontFamily = FontFamily(Font(R.font.montserrat_regular)),
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp

            ),
            start = indexStartOrientation,
            end = indexStartOrientation + 11
        )
        addStringAnnotation(
            tag = "URL",
            annotation = "https://delta.nitt.edu/",
            start = indexStartDelta,
            end = indexStartDelta + 11
        )
        addStringAnnotation(
            tag = "URL",
            annotation = "https://www.instagram.com/nitt.orientation/",
            start = indexStartOrientation,
            end = indexStartOrientation + 11
        )
    }
    return annotatedString
}

@Composable
fun Context.SnackShowError(errorMessage : String, modifier: Modifier) {
    val snackHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    SnackbarHost(snackHostState, modifier = modifier){ data ->
        Snackbar(
            snackbarData = data,
            containerColor = black,
            contentColor = white,
        )
    }
    fun showSnack() {
        scope.launch {
            snackHostState.showSnackbar(errorMessage, duration = SnackbarDuration.Short)
        }
    }
    showSnack()
}

@Composable
fun Context.SnackShowSuccess(errorMessage : String, modifier: Modifier) {
    val snackHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    SnackbarHost(snackHostState, modifier = modifier){ data ->
        Snackbar(
            snackbarData = data,
            containerColor = Color.Red,
            contentColor = black,
        )
    }
    fun showSnack() {
        scope.launch {
            snackHostState.showSnackbar(errorMessage, duration = SnackbarDuration.Short)
        }
    }
    showSnack()
}

@Composable
fun LoadingIcon() {
    CircularProgressIndicator(color = yellow)
}

val avatarList = mapOf(
    1 to R.drawable.bear,
    2 to R.drawable.cat,
    3 to R.drawable.dog,
    4 to R.drawable.giraffe,
    5 to R.drawable.panda,
)

val reverseAvatarList = mapOf(
    R.drawable.bear to 1,
    R.drawable.cat to 2,
    R.drawable.dog to 3,
    R.drawable.giraffe to 4,
    R.drawable.panda to 5,
)

val markerImages = mapOf(
    0 to R.drawable.one,
    1 to R.drawable.two,
    2 to R.drawable.three,
    3 to R.drawable.four,
    4 to R.drawable.five,
    5 to R.drawable.six,
)
