package edu.nitt.delta.orientation22.compose.screens
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*
import edu.nitt.delta.orientation22.R
import edu.nitt.delta.orientation22.constants.MapStyle
import edu.nitt.delta.orientation22.di.viewModel.actions.MapAction
import edu.nitt.delta.orientation22.di.viewModel.uiState.MapStateViewModel
import edu.nitt.delta.orientation22.models.MarkerModel

@Composable
fun GoogleMapScreen(markerList : List<MarkerModel>) {
    val lectureHallComplex = LatLng(10.7614246, 78.8139187)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.Builder().zoom(18.0f)
            .target(lectureHallComplex)
            .tilt(90.0f)
            .build()
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
                icon = BitmapDescriptorFactory.fromResource(R.drawable.twooo)
            )
        }
    }
}

@Composable
fun MapScreen(viewModel: MapStateViewModel){
    viewModel.doAction(MapAction.GetAllMarkers)
   var markerList = viewModel.markerListData
    GoogleMapScreen(markerList = markerList)
}
