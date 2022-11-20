package edu.nitt.delta.orientation22.di.viewModel.repository

import edu.nitt.delta.orientation22.di.api.ApiInterface
import edu.nitt.delta.orientation22.di.api.ResponseConstants
import edu.nitt.delta.orientation22.models.leaderboard.LeaderboardData
import javax.inject.Inject
import edu.nitt.delta.orientation22.models.Result

class LeaderBoardRepository @Inject constructor(
    private val apiInterface: ApiInterface
) {
    fun getLeaderBoard(token:String):Result<List<LeaderboardData>> =try {
        val response = apiInterface.getLeaderBord(token = token)
        if (response.message == ResponseConstants.SUCCESS) {
           Result.build { response.leaderboard }
        }
        Result.build { throw Exception(ResponseConstants.ERROR) }
    }catch (e:Exception){
        Result.build { throw e }
    }
}
