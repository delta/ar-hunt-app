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
    @SerializedName("avatar")
    val avatar : Int,
    @SerializedName("user_token")
    val token : String
){
    fun toMap(): Map<String, Any> =
        mapOf(
            "teamName" to teamName,
            "member2RollNo" to member2RollNo,
            "member2Name" to member2Name,
            "member3RollNo" to member3RollNo,
            "member3Name" to member3Name,
            "member4RollNo" to member4RollNo,
            "member4Name" to member4Name,
            "avatar" to avatar,
            "user_token" to token,
        )
}


