package edu.nitt.delta.orientation22.models.game

import com.google.gson.annotations.SerializedName

data class LocationRequest (
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double,
)