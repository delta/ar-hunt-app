package edu.nitt.delta.orientation22.di.viewModel.actions

sealed class TeamAction  {
    data class RegisterTeam(val teamData:Map<String,String>) : TeamAction()
    object GetTeam : TeamAction()
}
