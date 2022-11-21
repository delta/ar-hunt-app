package edu.nitt.delta.orientation22.models

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MarkerState

data class MarkerModel(
    val markerState: MarkerState = MarkerState(LatLng(9.917898824914543, 78.14629998707048)),
    val markerTitle : String = "Marker Title",
    val markerDescription : String = "Marker Description",
    val markerImage : Int,
    var isVisible : Boolean = false
)
