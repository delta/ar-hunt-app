package edu.nitt.delta.orientation22.models.game

import com.google.gson.annotations.SerializedName

data class LocationData(
    @SerializedName("Name")
    val name:String,
    @SerializedName("Clue")
    val clue:String,
    @SerializedName("AnchorHash")
    val anchorHash:String,
    @SerializedName("GlbURL")
    val glbUrl:String,
    @SerializedName("Answer")
    val answer:String,
    @SerializedName("RouteID")
    val routeId:String,
    @SerializedName("Position")
    val position:Int,
    @SerializedName("Latitude")
    val latitude:Double,
    @SerializedName("Longitude")
    val longitude:Double,
    @SerializedName("Scale")
    val scale:Double,
)