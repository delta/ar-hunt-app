package edu.nitt.delta.orientation22.di.viewModel.repository

import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.maps.android.compose.MarkerState
import edu.nitt.delta.orientation22.di.api.ApiInterface
import edu.nitt.delta.orientation22.di.api.ResponseConstants
import edu.nitt.delta.orientation22.di.storage.SharedPrefHelper
import edu.nitt.delta.orientation22.models.MarkerModel
import edu.nitt.delta.orientation22.models.Result
import edu.nitt.delta.orientation22.models.auth.TokenRequestModel
import edu.nitt.delta.orientation22.models.game.LocationData
import java.lang.reflect.Type
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
        Log.d("routes shared res",response.toString())
        val gson = Gson()
        val routeString = sharedPrefHelper.currentUserRoute
        Log.d("routes shared res",routeString.toString())

        if(routeString != "")
        {
            val locationDataType = object : TypeToken<List<LocationData>>() {}.type
            val routeList : List<LocationData> = gson.fromJson(routeString,locationDataType)
            Log.d("routes shared",routeList.toString())
            Log.d("routes shared",routeList[0].glbUrl)
            Result.build { routeList }
        }else {
            if (response.message == ResponseConstants.SUCCESS) {
                val locationData: List<LocationData> = response.locations
                Log.d("routes", locationData.toString())
                locationData.sortedWith(compareBy { it.position })
                sharedPrefHelper.currentUserRoute = gson.toJson(locationData)
                Result.build { locationData }
            } else {
                Result.build { throw Exception(ResponseConstants.ERROR) }
            }
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
        Result.build { throw Exception(ResponseConstants.ERROR) }
    }
}
