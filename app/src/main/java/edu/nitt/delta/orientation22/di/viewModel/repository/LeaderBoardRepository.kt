package edu.nitt.delta.orientation22.di.viewModel.repository

import android.util.Log
import edu.nitt.delta.orientation22.di.api.ApiInterface
import edu.nitt.delta.orientation22.di.api.ResponseConstants
import edu.nitt.delta.orientation22.di.storage.SharedPrefHelper
import edu.nitt.delta.orientation22.models.leaderboard.LeaderboardData
import javax.inject.Inject
import edu.nitt.delta.orientation22.models.Result
import edu.nitt.delta.orientation22.models.auth.TokenRequestModel

class LeaderBoardRepository @Inject constructor(
    private val apiInterface: ApiInterface
) {
    @Inject
    lateinit var sharedPrefHelper: SharedPrefHelper
    suspend fun getLeaderBoard():Result<List<LeaderboardData>> =try {

        val token = sharedPrefHelper.token.toString()
        Log.d("LeaderBoard",token)
        val response = apiInterface.getLeaderBord(TokenRequestModel(token))
        Log.d("LeaderBoard",response.toString())
        if (response.message == ResponseConstants.SUCCESS) {
           Result.build { response.leaderboard }
        }
        else {
            Result.build { throw Exception(ResponseConstants.ERROR) }
        }
    }catch (e:Exception){
        Result.build { throw Exception(ResponseConstants.ERROR) }
    }
}
