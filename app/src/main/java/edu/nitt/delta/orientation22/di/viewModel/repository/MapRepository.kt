package edu.nitt.delta.orientation22.di.viewModel.repository

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MarkerState
import edu.nitt.delta.orientation22.di.api.ApiInterface
import edu.nitt.delta.orientation22.models.MarkerModel
import edu.nitt.delta.orientation22.models.Result
import javax.inject.Inject

class MapRepository @Inject constructor(
    apiInterface: ApiInterface
){
    private val markerList : List<MarkerModel> = mutableListOf(
        MarkerModel(
            markerState = MarkerState(LatLng(10.7599262139634, 78.81060766093582)),
            markerTitle = "Orion",
            markerDescription = "A place where all starts",

            ),
        MarkerModel(
            markerState = MarkerState(LatLng(10.761512360192034, 78.81611227571871)),
            markerTitle = "Production Building",
            markerDescription = "production building",

            ),
        MarkerModel(
            markerState = MarkerState(LatLng(10.762649008007616, 78.81231815554612)),
            markerTitle = "Kailash Mess",
            markerDescription = "Kailash mess",
        ),
        MarkerModel(
            markerState = MarkerState(LatLng(10.76263364793056, 78.81489273709181)),
            markerTitle = "Chatzzz",
            markerDescription = "Chatzz kazana" ,

            )
    )
    fun getAllMarkers(): Result<List<MarkerModel>> = try {
         Result.build { markerList }
    }catch (e:Exception){
         Result.build { throw Exception(e) }
    }
}
