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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapStateViewModel @Inject constructor(
    private val mapRepository :MapRepository
): BaseViewModel<MapAction>() {
    private val markerListSample: List<MarkerModel> = listOf()
    var markerListData by mutableStateOf<List<MarkerModel>>(markerListSample)
    override fun doAction(action: MapAction): Any = when(action) {
        is MapAction.GetAllMarkers -> getAllMarkers()
    }

    private fun getAllMarkers() = launch {
        when(val res=mapRepository.getAllMarkers()){
            is Result.Value -> markerListData = res.value
            is Result.Error -> mutableError.value= res.exception.message
        }
    }
}
