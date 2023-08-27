package edu.nitt.delta.orientation22.models

import com.google.gson.annotations.SerializedName

data class IsLiveResponse(
    @SerializedName("live")
    val live: Boolean
):BaseResponse<String>()