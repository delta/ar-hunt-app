package edu.nitt.delta.orientation22.di.viewModel.repository

import edu.nitt.delta.orientation22.di.api.ApiInterface
import edu.nitt.delta.orientation22.di.api.ResponseConstants
import edu.nitt.delta.orientation22.models.Result
import edu.nitt.delta.orientation22.models.auth.TeamModel
import javax.inject.Inject

class TeamRepository @Inject constructor(
    private val apiInterface: ApiInterface
) {
    fun registerTeam(token: String,teamData : Map<String,String>):Result<String> = try {
        val response = apiInterface.registerTeam(token,teamData)
        if (response.message == ResponseConstants.SUCCESS){
            Result.build { response.message }
        }
        Result.build { throw Exception(ResponseConstants.ERROR) }
    }catch (e:Exception){
        Result.build { throw e}
    }

    fun getTeam(token:String):Result<TeamModel> = try {
        val response = apiInterface.getTeam(token)
        if(response.message == ResponseConstants.SUCCESS){
            val team = TeamModel(teamName = response.teamName, members = response.members)
            Result.build { team }
        }
        Result.build { throw Exception(ResponseConstants.ERROR)}
    }catch (e:Exception){
        Result.build { throw e }
    }
}
