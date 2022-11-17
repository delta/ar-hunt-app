package edu.nitt.delta.orientation22.di.viewModel.uiState

import dagger.hilt.android.lifecycle.HiltViewModel
import edu.nitt.delta.orientation22.di.viewModel.BaseViewModel
import edu.nitt.delta.orientation22.di.viewModel.actions.TeamAction
import edu.nitt.delta.orientation22.di.viewModel.repository.TeamRepository
import edu.nitt.delta.orientation22.models.Result
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamStateViewModel @Inject constructor(
    private val teamRepository: TeamRepository
) : BaseViewModel<TeamAction>(){
    override fun doAction(action: TeamAction): Any = when(action) {
        is TeamAction.GetTeam -> getTeam()
        is TeamAction.RegisterTeam->registerTeam(action.teamData)
    }

    private fun getTeam() =launch{
        val token = ""
        when(val res = teamRepository.getTeam(token)){
            is Result.Value -> mutableSuccess.value = res.value.toString()
            is Result.Error -> mutableError.value= res.exception.message
        }
    }

    private fun registerTeam(teamData:Map<String,String>) =launch{
        val token =""
        when(val res = teamRepository.registerTeam(token,teamData)){
            is Result.Value -> mutableSuccess.value = res.value
            is Result.Error -> mutableError.value= res.exception.message
        }
    }
}
