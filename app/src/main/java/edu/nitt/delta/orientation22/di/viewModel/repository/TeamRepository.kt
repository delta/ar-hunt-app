package edu.nitt.delta.orientation22.di.viewModel.repository

import android.util.Log
import com.google.gson.Gson
import edu.nitt.delta.orientation22.di.api.ApiInterface
import edu.nitt.delta.orientation22.di.api.ResponseConstants
import edu.nitt.delta.orientation22.di.storage.SharedPrefHelper
import edu.nitt.delta.orientation22.models.auth.TokenRequestModel
import edu.nitt.delta.orientation22.models.Result
import edu.nitt.delta.orientation22.models.auth.RegisterTeamRequest
import edu.nitt.delta.orientation22.models.auth.TeamModel
import javax.inject.Inject

class TeamRepository @Inject constructor(
    private val apiInterface: ApiInterface
) {
    @Inject
    lateinit var sharedPrefHelper: SharedPrefHelper
    suspend fun registerTeam(teamData : TeamModel):Result<String> = try {
        Log.d("TeamDetails",teamData.toString())
        val token = sharedPrefHelper.token.toString()
        Log.d("Team Details",token)

        val response = apiInterface.registerTeam(RegisterTeamRequest(token = token,
            teamName = teamData.teamName,
            member2Name = teamData.members[0].name,
            member2RollNo = teamData.members[0].rollNo,
            member3Name = teamData.members[1].name,
            member3RollNo = teamData.members[1].rollNo,
            member4Name = teamData.members[2].name,
            member4RollNo = teamData.members[2].rollNo,
            avatar = teamData.avatar
            ))
        Log.d("TeamDetails",response.message.toString())

        if (response.message == ResponseConstants.SUCCESS){
            Log.d("TeamDetails",response.message.toString())
            val gson = Gson()
            val teamString = gson.toJson(response.message)
            sharedPrefHelper.team = teamString
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
            Log.d("DashboardrepoShare",team.toString())
            Result.build { team }
        }
        else {
            val response = apiInterface.getTeam(TokenRequestModel(token.toString()))
            if (response.message == ResponseConstants.SUCCESS) {
                val team = TeamModel(teamName = response.teamName,
                    members = response.members,
                    avatar = 1,
                    points = 0)
                Log.d("Dashboardrepo", team.toString())
                val gson = Gson()
                val teamString = gson.toJson(team)
                sharedPrefHelper.team = teamString
                Result.build { team }
            } else {
                Log.d("Dashboardrepo", "Err")
                Result.build { throw Exception(ResponseConstants.ERROR) }
            }
        }
    }catch (e:Exception){
        Log.d("Dashboardrepo",e.message.toString())
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
