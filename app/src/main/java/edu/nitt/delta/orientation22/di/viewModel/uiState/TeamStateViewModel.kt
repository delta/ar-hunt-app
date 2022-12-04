package edu.nitt.delta.orientation22.di.viewModel.uiState

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.nitt.delta.orientation22.di.viewModel.BaseViewModel
import edu.nitt.delta.orientation22.di.viewModel.actions.TeamAction
import edu.nitt.delta.orientation22.di.viewModel.repository.TeamRepository
import edu.nitt.delta.orientation22.models.MarkerModel
import edu.nitt.delta.orientation22.models.Result
import edu.nitt.delta.orientation22.models.auth.Member
import edu.nitt.delta.orientation22.models.auth.TeamModel
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class RegistrationState{
    IDLE,
    LOADING,
    ERROR,
    SUCCESS,
}


@HiltViewModel
class TeamStateViewModel @Inject constructor(
    private val teamRepository: TeamRepository
) : BaseViewModel<TeamAction>(){

    private val teamDataSample: TeamModel = TeamModel(teamName = "", members =  listOf(
        Member(name = "",-1),Member(name = "",-1),Member(name = "",-1),Member(name = "",-1)), points = 0, avatar = 1)
    var teamData = mutableStateOf(teamDataSample)
    var uiState = mutableStateOf(RegistrationState.IDLE)

    override fun doAction(action: TeamAction): Any = when(action) {
        is TeamAction.GetTeam -> getTeam()
        is TeamAction.RegisterTeam->registerTeam(action.teamData)
        is TeamAction.GetLeader -> register()
    }
    private fun getTeam() =launch{
        when(val res = teamRepository.getTeam()){
            is Result.Value -> {
                Log.d("Dashboard",res.value.toString())
                Log.v("Dashboard","hi")
                teamData.value = res.value
            }
            is Result.Error -> mutableError.value= res.exception.message
        }
    }

    private fun register(){
        var res = teamRepository.getLeader()
        teamData.value=TeamModel(teamName = "", members = listOf(res,Member(name = "",-1),Member(name = "",-1),Member(name = "",-1)), points = 0, avatar = 1)
        Log.v("2222",teamData.value.toString())
    }

    private fun registerTeam(teamData:TeamModel) =launch{
        uiState.value =RegistrationState.LOADING
        when(val res = teamRepository.registerTeam(teamData)){
            is Result.Value -> {
                Log.v("123","123")
                uiState.value =RegistrationState.SUCCESS
                mutableSuccess.value = res.value
            }
            is Result.Error -> {
                uiState.value =RegistrationState.ERROR
                mutableError.value= res.exception.message
            }
        }
    }
}
