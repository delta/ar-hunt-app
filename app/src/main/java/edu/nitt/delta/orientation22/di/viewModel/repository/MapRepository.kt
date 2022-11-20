package edu.nitt.delta.orientation22.di.viewModel.repository

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MarkerState
import edu.nitt.delta.orientation22.di.api.ApiInterface
import edu.nitt.delta.orientation22.di.api.ResponseConstants
import edu.nitt.delta.orientation22.di.storage.SharedPrefHelper
import edu.nitt.delta.orientation22.models.MarkerModel
import edu.nitt.delta.orientation22.models.Result
import edu.nitt.delta.orientation22.models.game.LocationData
import javax.inject.Inject

class MapRepository @Inject constructor(
    private val apiInterface: ApiInterface
){
    @Inject
    lateinit var sharedPrefHelper: SharedPrefHelper
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

    fun getRoutes(token: String):Result<List<MarkerModel>> = try {
        val response = apiInterface.getRoute(token)
        val markerList = mutableListOf<List<MarkerModel>>()
        if(response.message == ResponseConstants.SUCCESS){
            val locationData : List<LocationData> = response.locations
            locationData.forEach {
                markerList.add(listOf(MarkerModel(markerState = MarkerState(LatLng(it.latitude,it.longitude)), markerTitle = it.name, markerDescription = it.clue)))
            }
            Result.build { markerList }
        }
        Result.build { throw Exception(ResponseConstants.ERROR) }
    }catch (e:Exception){
        Result.build { throw e }
    }

    fun getCurrentLevel(token:String):Result<Int> = try {
        val response = apiInterface.getCurrentLevel(token)
        if(response.message == ResponseConstants.SUCCESS){
            Result.build { response.currentLevel }
        }
        Result.build { throw Exception(ResponseConstants.ERROR) }
    }catch (e:Exception){
        Result.build { throw e }
    }
}
