package edu.nitt.delta.orientation22.compose

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.window.Dialog
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
    currentLevel: Int,
) {
    when{
        permissionState.hasPermission -> {
            distanceCalculator(fusedLocationProviderClient, mContext, currentClueLocation,glbUrl,anchorHash,currentScale, currentLevel)
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
fun PermissionGetter(
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
    val screenHeight = LocalConfiguration.current.screenHeightDp
    if (showDialog) {
        Dialog(onDismissRequest = onDismiss) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(5))
                    .paint(
                        painter = painterResource(id = R.drawable.dialog_background),
                        contentScale = ContentScale.FillBounds
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                    Image(
                        painter = painterResource(id = R.drawable.skull_banner),
                        contentDescription = "Skull Banner",
                        modifier = Modifier
                            .height((screenHeight / 10).dp)
                            .width((screenHeight / 10).dp),
                        contentScale = ContentScale.FillHeight
                    )
                Spacer(modifier = Modifier.height((screenHeight / 30).dp))
                Text(
                    text = clueDescription,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Normal,
                    color = white,
                    fontFamily = FontFamily(Font(R.font.fiddlerscove)),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Spacer(modifier = Modifier.height((screenHeight / 40).dp))
                TextButton(
                    onClick = onDismiss,
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = red)
                ) {
                    Text(
                        text = "CLOSE",
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
}

fun distanceCalculator(
    fusedLocationProviderClient: FusedLocationProviderClient,
    mContext: Context,
    currentClueLocation: LatLng,
    glbUrl: String,
    anchorHash: String,
    currentScale: Double,
    currentLevel: Int,
){
    val innerRadius = 40
    val outerRadius = 70

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
                if (distanceInMeters <= innerRadius) {
                    val intent = Intent(mContext, ArActivity::class.java)
                    intent.putExtra("glb", glbUrl)
                    intent.putExtra("anchorHash", anchorHash)
                    intent.putExtra("anchorScale", currentScale)
                    intent.putExtra("level", currentLevel)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    mContext.startActivity(intent)
                } else if (distanceInMeters <= outerRadius){
                    mContext.toast("You are almost there.")
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
        val str = "MADE WITH ❤ BY DELTA FORCE"
        val indexStartDelta = str.indexOf("DELTA FORCE")
        append(str)
        addStyle(
            style = SpanStyle(
                color = white,
                fontFamily = FontFamily(Font(R.font.daysone_regular)),
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp
            ),
            start = 0,
            end = str.length - 1
        )
        addStyle(
            style = SpanStyle(
                color = color,
                fontFamily = FontFamily(Font(R.font.daysone_regular)),
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp

            ),
            start = indexStartDelta,
            end = indexStartDelta+11
        )
        addStringAnnotation(
            tag = "URL",
            annotation = "https://delta.nitt.edu/",
            start = indexStartDelta,
            end = indexStartDelta + 11
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T : SpeedDialData> SpeedDialFloatingActionButton(
    modifier: Modifier = Modifier,
    initialExpanded: Boolean = false,
    animationDuration: Int = 300,
    animationDelayPerSelection: Int = 100,
    speedDialData: List<T>,
    onClick: (T?) -> Unit = {},
    showLabels: Boolean = false,
    fabBackgroundColor: Color = MaterialTheme.colorScheme.secondary,
    fabContentColor: Color = contentColorFor(fabBackgroundColor),
    speedDialBackgroundColor: Color = MaterialTheme.colorScheme.secondary,
    speedDialContentColor: Color = contentColorFor(speedDialBackgroundColor),
) {
    var expanded by remember { mutableStateOf(initialExpanded) }

    val transition = updateTransition(label = "multiSelectionExpanded", targetState = expanded)

    val speedDialAlpha = mutableListOf<State<Float>>()
    val speedDialScale = mutableListOf<State<Float>>()

    speedDialData.forEachIndexed { index, _ ->

        speedDialAlpha.add(
            transition.animateFloat(
                label = "multiSelectionAlpha",
                transitionSpec = {
                    tween(
                        delayMillis = index * animationDelayPerSelection,
                        durationMillis = animationDuration
                    )
                }
            ) {
                if (it) 1f else 0f
            }
        )

        speedDialScale.add(
            transition.animateFloat(
                label = "multiSelectionScale",
                transitionSpec = {
                    tween(
                        delayMillis = index * animationDelayPerSelection,
                        durationMillis = animationDuration
                    )
                }
            ) {
                if (it) 1f else 0f
            }
        )
    }

    val fabIconRotation by transition.animateFloat(
        label = "fabIconRotation",
        transitionSpec = {
            tween(durationMillis = animationDuration)
        }
    ) {
        if (it) 90f else 0f
    }
    val fabBackgroundColorAnimated by transition.animateColor(
        label = "fabBackgroundColor",
        transitionSpec = {
            tween(durationMillis = animationDuration)
        }
    ) {
        if (it) black else fabBackgroundColor
    }

    val fabContentColorAnimated by transition.animateColor(
        label = "fabContentColor",
        transitionSpec = {
            tween(durationMillis = animationDuration)
        }
    ) {
        if (it) white else fabContentColor
    }

    Layout(
        modifier = modifier,
        content = {
            FloatingActionButton(
                onClick = {
                    expanded = !expanded

                    if (speedDialData.isEmpty()) {
                        onClick(null)
                    }
                },
                containerColor = fabBackgroundColorAnimated,
                contentColor = fabContentColorAnimated
            ) {
                Icon(
                    modifier = Modifier.rotate(fabIconRotation),
                    imageVector = Icons.Default.Menu,
                    contentDescription = null,
                )
            }

            speedDialData.forEachIndexed { index, data ->

                val correctIndex =
                    if (expanded) index else speedDialData.size - 1 - index

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    val interactionSource = remember { MutableInteractionSource() }
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .alpha(speedDialAlpha[correctIndex].value)
                            .scale(speedDialScale[correctIndex].value)
                    ) {
                        ExtendedFloatingActionButton(
                            text = {
                                Text(
                                    text = data.label,
                                    color = speedDialContentColor,
                                    maxLines = 1,
                                    fontFamily = FontFamily(Font(R.font.daysone_regular)),
                                    fontSize = 15.sp
                                ) },
                            icon = { Icon(
                                painter = painterResource(id = data.painterResource),
                                tint = speedDialContentColor,
                                contentDescription = null,
                                modifier = Modifier.size(40.dp)
                            ) },
                            onClick = {
                                onClick(data)
                                data.onClick()
                            },
                            interactionSource = interactionSource,
                            containerColor = speedDialBackgroundColor,
                            contentColor = speedDialContentColor,
                        )
                    }
                    Spacer(modifier = Modifier.requiredWidth(10.dp))
                }
            }
        }
    ) { measurables, constraints ->

        val fab = measurables[0]
        val subFabs = measurables.subList(1, measurables.count())

        val fabPlacable = fab.measure(constraints)

        val subFabPlacables = subFabs.map {
            it.measure(constraints)
        }

        layout(
            width = fabPlacable.width,
            height = fabPlacable.height
        ) {
            fabPlacable.placeRelative(0, 0)

            subFabPlacables.forEachIndexed { index, placeable ->

                if (transition.isRunning or transition.currentState) {
                    placeable.placeRelative(
                        x = fabPlacable.width - placeable.width,
                        y = -index * placeable.height - (fabPlacable.height * 1.25f).toInt()
                    )
                }
            }
        }
    }
}

open class SpeedDialData(
    val label: String,
    @DrawableRes
    val painterResource: Int,
    val scale: Float,
    val onClick: () -> Unit,
)

@Composable
fun LoadingIcon() {
    CircularProgressIndicator(color = black)
}

val avatarList = mapOf(
    1 to R.drawable.avatar1,
    2 to R.drawable.avatar2,
    3 to R.drawable.avatar3,
    4 to R.drawable.avatar4,
)

val reverseAvatarList = mapOf(
    R.drawable.avatar1 to 1,
    R.drawable.avatar2 to 2,
    R.drawable.avatar3 to 3,
    R.drawable.avatar4 to 4,
)

val markerImages = mapOf(
    0 to R.drawable.one,
    1 to R.drawable.two,
    2 to R.drawable.three,
    3 to R.drawable.four,
    4 to R.drawable.five,
    5 to R.drawable.six,
)
