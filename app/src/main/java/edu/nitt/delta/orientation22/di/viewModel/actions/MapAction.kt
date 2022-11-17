package edu.nitt.delta.orientation22.di.viewModel.actions

sealed class MapAction  {
     object GetAllMarkers : MapAction()
     object GetRoute : MapAction()
     object GetCurrentLevel: MapAction()
}
