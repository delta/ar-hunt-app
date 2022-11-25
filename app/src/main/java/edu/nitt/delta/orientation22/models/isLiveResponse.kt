package edu.nitt.delta.orientation22.models

import com.google.gson.annotations.SerializedName

data class IsLiveResponse(
    @SerializedName("isLive")
    val live: Boolean
):BaseResponse<String>()