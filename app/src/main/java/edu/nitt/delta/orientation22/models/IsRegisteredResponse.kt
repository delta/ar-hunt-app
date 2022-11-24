package edu.nitt.delta.orientation22.models

import com.google.gson.annotations.SerializedName

data class IsRegisteredResponse(
    @SerializedName("isRegistered")
    val isRegistered: Boolean,
    @SerializedName("name")
    val name:String,
    @SerializedName("rollNo")
    val rollNo: Int
):BaseResponse<String>()