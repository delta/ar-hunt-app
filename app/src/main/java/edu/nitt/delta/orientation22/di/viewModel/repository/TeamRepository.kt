package edu.nitt.delta.orientation22.di.viewModel.repository

import android.util.Log
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
            member2Name = if (teamData.members.isNotEmpty()) teamData.members[0].name else "",
            member2RollNo = if (teamData.members.isNotEmpty()) teamData.members[0].rollNo else 0,
            member3Name = if (teamData.members.size > 1) teamData.members[1].name else "",
            member3RollNo = if (teamData.members.size > 1) teamData.members[1].rollNo else 0,
            member4Name = if (teamData.members.size > 2) teamData.members[2].name else "",
            member4RollNo = if (teamData.members.size > 2) teamData.members[2].rollNo else 0,
            avatar = teamData.avatar
            ))
        Log.d("TeamDetails",response.message.toString())

        if (response.message == ResponseConstants.REGISTRATION_SUCCESS){
            Result.build { response.message.toString() }
        } else {
            Result.build { throw Exception(response.message.toString()) }
        }
    }catch (e:Exception){
        Log.d("TeamDetails",e.message.toString())
        Result.build { throw Exception(ResponseConstants.ERROR)}
    }

    suspend fun getTeam():Result<TeamModel> = try {
        var token = sharedPrefHelper.token
        val response = apiInterface.getTeam(TokenRequestModel(token.toString()))
        Log.v("123",response.toString())
        if (response.message == ResponseConstants.GET_TEAM_DETAILS_SUCCESS) {
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
