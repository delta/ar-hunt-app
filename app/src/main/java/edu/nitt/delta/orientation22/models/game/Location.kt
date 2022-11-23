package edu.nitt.delta.orientation22.models.game

import com.google.gson.annotations.SerializedName
import edu.nitt.delta.orientation22.models.BaseResponse


data class LocationFetchResponse(
    @SerializedName("locations")
    val locations:List<Location>
):BaseResponse<String>()

data class Location(
    @SerializedName("id")
    val id :Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("glburl")
    val glbUrl: String,
)
