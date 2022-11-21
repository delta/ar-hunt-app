package edu.nitt.delta.orientation22.di.api

import edu.nitt.delta.orientation22.models.auth.*
import edu.nitt.delta.orientation22.models.game.AnswerResponse
import edu.nitt.delta.orientation22.models.game.CurrentLocationResponse
import edu.nitt.delta.orientation22.models.game.RouteResponse
import edu.nitt.delta.orientation22.models.leaderboard.LeaderboardResponse
import retrofit2.http.*

interface ApiInterface {

    @POST(ApiRoutes.LEADERBOARD)
    suspend fun getLeaderBord(
        @Body body: TokenRequestModel
    ):LeaderboardResponse


    @POST(ApiRoutes.REGISTER_TEAM)
    suspend fun registerTeam(
        @Body registerTeamBody : RegisterTeamRequest
    ):RegisterTeamResponse

    @POST(ApiRoutes.TEAM)
    suspend fun getTeam(
        @Body body: TokenRequestModel
    ):TeamDataResponse

    @GET(ApiRoutes.ROUTE)
    fun getRoute(
        @Field("user_token") token: String,
    ):RouteResponse

    @GET(ApiRoutes.CURRENT_STATE)
    fun getCurrentLevel(
        @Field("user_token") token: String,
    ):CurrentLocationResponse

    @POST(ApiRoutes.NEXT_STATE)
    fun postAnswer(
        @Field("user_token") token: String,
    ):AnswerResponse

    @GET(ApiRoutes.AUTH_CALLBACK)
    suspend fun login(
        @Query("code") code:String
    ):LoginResponse


}
