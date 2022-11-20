package edu.nitt.delta.orientation22.fragments

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import edu.nitt.delta.orientation22.compose.screens.MapScreen
import edu.nitt.delta.orientation22.di.viewModel.actions.MapAction
import edu.nitt.delta.orientation22.di.viewModel.uiState.MapStateViewModel

@Composable
fun MapFragment(
    mapviewModel: MapStateViewModel,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ){
        mapviewModel.doAction(MapAction.GetAllMarkers)
        var markerList = mapviewModel.markerListData
        MapScreen(markerList)
    }
}
