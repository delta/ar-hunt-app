package edu.nitt.delta.orientation22.di.viewModel.actions

import edu.nitt.delta.orientation22.models.auth.TeamModel

sealed class TeamAction  {
    data class RegisterTeam(val teamData:TeamModel) : TeamAction()
    object GetTeam : TeamAction()
    object GetLeader : TeamAction()
}
