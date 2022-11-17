package edu.nitt.delta.orientation22.models.game

import com.google.gson.annotations.SerializedName
import edu.nitt.delta.orientation22.models.BaseResponse

data class RouteResponse(
    @SerializedName("locations")
    val locations:List<LocationData>
):BaseResponse<String>()
