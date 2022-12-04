package edu.nitt.delta.orientation22.models.auth

import com.google.gson.annotations.SerializedName
import edu.nitt.delta.orientation22.models.BaseResponse

data class TeamDataResponse(
    @SerializedName("member")
    val members:List<Member>,
    @SerializedName("team_name")
    val teamName:String,
    @SerializedName("avatar")
    val avatar:Int,
    @SerializedName("score")
    val points: Int
):BaseResponse<String>()
