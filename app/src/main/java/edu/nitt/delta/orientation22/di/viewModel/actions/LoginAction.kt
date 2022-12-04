package edu.nitt.delta.orientation22.di.viewModel.actions

sealed class LoginAction {
    data class Login(val code : String):LoginAction()
    object IsLoggedIn : LoginAction()
    object IsLoggedOut : LoginAction()
    object IsRegistered : LoginAction()
    object IsLive : LoginAction()
}
