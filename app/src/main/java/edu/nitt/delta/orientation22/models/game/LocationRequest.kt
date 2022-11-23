package edu.nitt.delta.orientation22.models.game

import com.google.gson.annotations.SerializedName

data class LocationRequest(
    @SerializedName("anchorHash")
    val anchorHash:String,
    @SerializedName("id")
    val id:Int,
    @SerializedName("Latitude")
    val latitude:Double,
    @SerializedName("Longitude")
    val longitude:Double,
    @SerializedName("user_token")
    val userToken:String
)
