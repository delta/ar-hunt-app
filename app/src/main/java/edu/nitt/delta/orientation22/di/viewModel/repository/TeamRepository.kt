package edu.nitt.delta.orientation22.di.viewModel.repository

import android.util.Log
import com.google.gson.Gson
import edu.nitt.delta.orientation22.di.api.ApiInterface
import edu.nitt.delta.orientation22.di.api.ResponseConstants
import edu.nitt.delta.orientation22.di.storage.SharedPrefHelper
import edu.nitt.delta.orientation22.models.auth.TokenRequestModel
import edu.nitt.delta.orientation22.models.Result
import edu.nitt.delta.orientation22.models.auth.Member
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
            Result.build { response.message.toString() }
        } else {
            Result.build { throw Exception(ResponseConstants.ERROR) }
        }
    }catch (e:Exception){
        Log.d("TeamDetails",e.message.toString())
        Result.build { throw Exception(ResponseConstants.ERROR)}
    }

    suspend fun getTeam():Result<TeamModel> = try {
        var token = sharedPrefHelper.token
        val response = apiInterface.getTeam(TokenRequestModel(token.toString()))
        Log.v("123",response.toString())
        if (response.message == ResponseConstants.SUCCESS) {
            val team = TeamModel(teamName = response.teamName,
                members = response.members,
                avatar = response.avatar,
                points = response.points)
            Log.d("Dashboardrepo", team.toString())
            Result.build { team }
        } else {
            Log.d("Dashboardrepo", "Err")
            Result.build { throw Exception(ResponseConstants.ERROR) }
        }
    }catch (e:Exception){
        Log.d("Dashboardrepo",e.message.toString())
        Result.build { throw Exception(ResponseConstants.ERROR) }
    }

    fun getLeader():Member{
        return Member(name = sharedPrefHelper.leaderName.toString(), rollNo = sharedPrefHelper.rollNo)
    }
}
