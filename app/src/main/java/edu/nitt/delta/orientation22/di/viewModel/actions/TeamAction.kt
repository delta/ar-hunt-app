package edu.nitt.delta.orientation22.di.viewModel.actions

sealed class TeamAction  {
    data class RegisterTeam(val teamData:Map<String,Any>) : TeamAction()
    object GetTeam : TeamAction()
    object IsTeamPresent : TeamAction()
}
