package edu.nitt.delta.orientation22.di.viewModel.actions

import android.content.Context

sealed class LoginAction {
    data class Login(val code : String):LoginAction()
    object IsLoggedIn : LoginAction()
    object IsLoggedOut : LoginAction()
    object IsRegistered : LoginAction()
    object IsLive : LoginAction()
    data class DownloadAssets(val urls: List<String>, val context: Context): LoginAction()
    object IsDownloaded : LoginAction()
}
