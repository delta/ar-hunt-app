package edu.nitt.delta.orientation22.di.viewModel.repository

import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MarkerState
import edu.nitt.delta.orientation22.di.api.ApiInterface
import edu.nitt.delta.orientation22.di.api.ResponseConstants
import edu.nitt.delta.orientation22.di.storage.SharedPrefHelper
import edu.nitt.delta.orientation22.models.MarkerModel
import edu.nitt.delta.orientation22.models.Result
import edu.nitt.delta.orientation22.models.auth.TokenRequestModel
import edu.nitt.delta.orientation22.models.game.LocationData
import javax.inject.Inject

class MapRepository @Inject constructor(
    private val apiInterface: ApiInterface
){
    @Inject
    lateinit var sharedPrefHelper: SharedPrefHelper
    private val markerList : List<MarkerModel> = mutableListOf()
    fun getAllMarkers(): Result<List<MarkerModel>> = try {
         Result.build { markerList }
    }catch (e:Exception){
         Result.build { throw Exception(e) }
    }

    suspend fun getRoutes():Result<List<LocationData>> = try {
        val token = sharedPrefHelper.token.toString()
        val response = apiInterface.getRoute(TokenRequestModel(token))
        if(response.message == ResponseConstants.SUCCESS){
            val locationData : List<LocationData> = response.locations
            Result.build { locationData }
        }else {
            Result.build { throw Exception(ResponseConstants.ERROR) }
        }
    }catch (e:Exception){
        Result.build { throw e }
    }

    suspend fun getCurrentLevel():Result<Int> = try {
        val token = sharedPrefHelper.token.toString()
        val response = apiInterface.getCurrentLevel(TokenRequestModel(token))
        if(response.message == ResponseConstants.SUCCESS){
            Result.build { response.currentLevel }
        }
        else {
            Result.build { throw Exception(ResponseConstants.ERROR) }
        }
    }catch (e:Exception){
        Result.build { throw e }
    }
}
