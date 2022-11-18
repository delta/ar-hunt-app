package edu.nitt.delta.orientation22.di.viewModel.uiState

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.nitt.delta.orientation22.di.viewModel.BaseViewModel
import edu.nitt.delta.orientation22.di.viewModel.actions.MapAction
import edu.nitt.delta.orientation22.di.viewModel.repository.MapRepository
import edu.nitt.delta.orientation22.models.MarkerModel
import edu.nitt.delta.orientation22.models.Result
import edu.nitt.delta.orientation22.models.game.LocationData
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapStateViewModel @Inject constructor(
    private val mapRepository :MapRepository
): BaseViewModel<MapAction>() {
    private val markerListSample: List<MarkerModel> = listOf()
    private val routeListSample: List<LocationData> = listOf()

    var markerListData by mutableStateOf<List<MarkerModel>>(markerListSample)
    var routeListData by mutableStateOf<List<LocationData>>(routeListSample)

    override fun doAction(action: MapAction): Any = when(action) {
        is MapAction.GetAllMarkers -> getAllMarkers()
        is MapAction.GetRoute -> getRoute()
        is MapAction.GetCurrentLevel -> getCurrentLevel()
    }

    private fun getAllMarkers() = launch {
        when(val res=mapRepository.getAllMarkers()){
            is Result.Value -> markerListData = res.value
            is Result.Error -> mutableError.value= res.exception.message
        }
    }

    private fun getCurrentLevel() = launch {
        val token = ""
        when(val res=mapRepository.getCurrentLevel(token)){
            is Result.Value -> mutableSuccess.value = res.value.toString()
            is Result.Error -> mutableError.value= res.exception.message
        }
    }
    private fun getRoute() = launch {
        val token = ""
        when(val res=mapRepository.getRoutes(token)){
            is Result.Value -> markerListData = res.value
            is Result.Error -> mutableError.value= res.exception.message
        }
    }
}
