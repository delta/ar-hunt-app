package edu.nitt.delta.orientation22.models.auth

import com.google.gson.annotations.SerializedName

data class Member(
    @SerializedName("name")
    val name:String,
    @SerializedName("roll_no")
    val rollNo:Int
)
