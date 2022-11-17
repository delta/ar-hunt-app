package edu.nitt.delta.orientation22.models.auth

import com.google.gson.annotations.SerializedName

data class RegisterTeamRequest(
    @SerializedName("teamName")
    val teamName:String,
    @SerializedName("member2RollNo")
    val member2RollNo:Int,
    @SerializedName("member2Name")
    val member2Name:String,
    @SerializedName("member3RollNo")
    val member3RollNo:Int,
    @SerializedName("member3Name")
    val member3Name:String,
    @SerializedName("member4RollNo")
    val member4RollNo:Int,
    @SerializedName("member4Name")
    val member4Name:String,
)
