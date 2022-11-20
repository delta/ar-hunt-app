package edu.nitt.delta.orientation22.di.viewModel.repository

import com.google.gson.Gson
import edu.nitt.delta.orientation22.di.api.ApiInterface
import edu.nitt.delta.orientation22.di.api.ResponseConstants
import edu.nitt.delta.orientation22.di.storage.SharedPrefHelper
import edu.nitt.delta.orientation22.models.Result
import edu.nitt.delta.orientation22.models.auth.TeamModel
import javax.inject.Inject

class TeamRepository @Inject constructor(
    private val apiInterface: ApiInterface
) {
    @Inject
    lateinit var sharedPrefHelper: SharedPrefHelper
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
        if(sharedPrefHelper.team == "")
        {
            val gson = Gson()
            val team = gson.fromJson(sharedPrefHelper.team,TeamModel::class.java)
            Result.build { team }
        }
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
