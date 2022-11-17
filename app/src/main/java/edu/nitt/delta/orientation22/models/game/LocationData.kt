package edu.nitt.delta.orientation22.models.game

import com.google.gson.annotations.SerializedName

data class LocationData(
    @SerializedName("")
    val name:String,
    @SerializedName("")
    val clue:String,
    @SerializedName("")
    val anchorHash:String,
    @SerializedName("")
    val glbUrl:String,
    @SerializedName("")
    val answer:String,
    @SerializedName("")
    val routeId:String,
    @SerializedName("")
    val position:Int,
    @SerializedName("")
    val latitude:Double,
    @SerializedName("")
    val longitude:Double
)
