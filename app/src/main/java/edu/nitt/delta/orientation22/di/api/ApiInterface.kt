package edu.nitt.delta.orientation22.di.api

import edu.nitt.delta.orientation22.models.auth.LoginResponse
import edu.nitt.delta.orientation22.models.auth.RegisterTeamResponse
import edu.nitt.delta.orientation22.models.auth.TeamDataResponse
import edu.nitt.delta.orientation22.models.game.AnswerResponse
import edu.nitt.delta.orientation22.models.game.CurrentLocationResponse
import edu.nitt.delta.orientation22.models.game.RouteResponse
import edu.nitt.delta.orientation22.models.leaderboard.LeaderboardResponse
import retrofit2.http.*

interface ApiInterface {

    @POST(ApiRoutes.LEADERBOARD)
    fun getLeaderBord(
        @Field("user_token") token: String,
    ):LeaderboardResponse

    @FormUrlEncoded
    @JvmSuppressWildcards
    @POST(ApiRoutes.REGISTER_TEAM)
    suspend fun registerTeam(
        @Field("user_token") token: String,
        @FieldMap parameters: Map<String, Any>
    ):RegisterTeamResponse

    @FormUrlEncoded
    @POST(ApiRoutes.TEAM)
    suspend fun getTeam(
        @Field("user_token") token: String,
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
