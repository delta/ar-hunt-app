package edu.nitt.delta.orientation22.di.api

import edu.nitt.delta.orientation22.models.IsLiveResponse
import edu.nitt.delta.orientation22.models.IsRegisteredResponse
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
        @Body teamBody: TokenRequestModel
    ):TeamDataResponse

    @POST(ApiRoutes.ROUTE)
    suspend fun getRoute(
        @Body routeBody: TokenRequestModel
    ):RouteResponse

    @POST(ApiRoutes.CURRENT_STATE)
    suspend fun getCurrentLevel(
        @Body currentLevelBody: TokenRequestModel
    ):CurrentLocationResponse

    @POST(ApiRoutes.NEXT_STATE)
    suspend fun postAnswer(
        @Body postAnswerBody: TokenRequestModel
    ):AnswerResponse

    @GET(ApiRoutes.AUTH_CALLBACK)
    suspend fun login(
        @Query("code") code:String
    ):LoginResponse

    @POST(ApiRoutes.IS_REGISTERED)
    suspend fun isRegistered(
        @Body token:TokenRequestModel
    ):IsRegisteredResponse

    @POST(ApiRoutes.IS_LIVE)
    suspend fun isLive(
        @Body token: TokenRequestModel
    ):IsLiveResponse
}
