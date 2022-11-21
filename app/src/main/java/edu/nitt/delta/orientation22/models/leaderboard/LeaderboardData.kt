package edu.nitt.delta.orientation22.models.leaderboard

import com.google.gson.annotations.SerializedName

data class LeaderboardData(
    @SerializedName("team_name")
    val teamName: String,

    @SerializedName("score")
    val score:Int,

    @SerializedName("avatar")
    val avatar : Int,
)
