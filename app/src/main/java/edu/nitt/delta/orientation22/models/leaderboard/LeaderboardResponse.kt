package edu.nitt.delta.orientation22.models.leaderboard

import com.google.gson.annotations.SerializedName
import edu.nitt.delta.orientation22.models.BaseResponse

data class LeaderboardResponse(
    @SerializedName("leaderboard")
    val leaderboard: List<LeaderboardData>
):BaseResponse<String>()
