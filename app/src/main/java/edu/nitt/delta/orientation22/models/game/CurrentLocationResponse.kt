package edu.nitt.delta.orientation22.models.game

import com.google.gson.annotations.SerializedName
import edu.nitt.delta.orientation22.models.BaseResponse

data class CurrentLocationResponse(
    @SerializedName("currentState")
    val currentLevel:Int
):BaseResponse<String>()
