package edu.nitt.delta.orientation22.di.viewModel.uiState

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


@HiltViewModel
class TeamStateViewModel @Inject constructor(
    private val teamRepository: TeamRepository
) : BaseViewModel<TeamAction>(){

    private val teamDataSample: TeamModel = TeamModel(teamName = "", members =  listOf(
        Member(name = "adsasd",110120089),Member(name = "Member1",110120090),Member(name = "Member2",110120091),Member(name = "Member3",110120073)), points = 0)
    var teamData by mutableStateOf(teamDataSample)

    override fun doAction(action: TeamAction): Any = when(action) {
        is TeamAction.GetTeam -> getTeam()
        is TeamAction.RegisterTeam->registerTeam(action.teamData)
        is TeamAction.IsTeamPresent -> IsTeamPresentt()
    }
    var isTeamPresent = false
    private fun getTeam() =launch{
        when(val res = teamRepository.getTeam()){
            is Result.Value -> teamData = res.value
            is Result.Error -> mutableError.value= res.exception.message
        }
    }

    private fun registerTeam(teamData:Map<String,Any>) =launch{
        when(val res = teamRepository.registerTeam(teamData)){
            is Result.Value -> mutableSuccess.value = res.value
            is Result.Error -> mutableError.value= res.exception.message
        }
    }

    private fun IsTeamPresentt() = launch {
        when(val res = teamRepository.isTeamPresent()){
            is edu.nitt.delta.orientation22.models.Result.Value -> isTeamPresent = res.value
            is edu.nitt.delta.orientation22.models.Result.Error -> mutableError.value = res.exception.message
        }
    }
}
