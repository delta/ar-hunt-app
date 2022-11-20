package edu.nitt.delta.orientation22.di.viewModel.repository

import android.util.Log
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
    suspend fun registerTeam(teamData : Map<String,Any>):Result<String> = try {
        Log.d("TeamDetails",teamData.toString())
        val token = sharedPrefHelper.token.toString()
        Log.d("Team Details",token)

        val response = apiInterface.registerTeam(token,teamData)
        Log.d("TeamDetails",response.message.toString())

        if (response.message == ResponseConstants.SUCCESS){
            Log.d("TeamDetails",response.message.toString())
            Result.build { response.message }
        }
        Result.build { throw Exception(ResponseConstants.ERROR) }
    }catch (e:Exception){
        Log.d("TeamDetails",e.message.toString())
        Result.build { throw e}
    }

    suspend fun getTeam():Result<TeamModel> = try {
        var token = sharedPrefHelper.token
        if(sharedPrefHelper.team != "")
        {
            val gson = Gson()
            val team = gson.fromJson(sharedPrefHelper.team,TeamModel::class.java)
            Result.build { team }
        }
        Log.d("Getteam",token.toString())

        val response = apiInterface.getTeam(token.toString())
        Log.d("Getteam",response.teamName)
        if(response.message == ResponseConstants.SUCCESS){
            val team = TeamModel(teamName = response.teamName, members = response.members)
            val gson = Gson()
            val teamString = gson.toJson(team)
            sharedPrefHelper.team = teamString
            Result.build { team }
        }
        Result.build { throw Exception(ResponseConstants.ERROR)}
    }catch (e:Exception){
        Log.d("GetTeam",e.message.toString())
        Result.build { throw e }
    }

    fun isTeamPresent():edu.nitt.delta.orientation22.models.Result<Boolean> = try {
        val team = sharedPrefHelper.team
        if(team != "")
            edu.nitt.delta.orientation22.models.Result.build { true }
        else
            edu.nitt.delta.orientation22.models.Result.build { false }
    }catch (e:Exception){
        edu.nitt.delta.orientation22.models.Result.build { throw e }
    }
}
