package edu.nitt.delta.orientation22.di.viewModel.uiState

import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.nitt.delta.orientation22.di.viewModel.BaseViewModel
import edu.nitt.delta.orientation22.di.viewModel.actions.LoginAction
import edu.nitt.delta.orientation22.di.viewModel.repository.LoginRepository
import edu.nitt.delta.orientation22.models.IsRegisteredResponse
import edu.nitt.delta.orientation22.models.Result
import edu.nitt.delta.orientation22.models.auth.UserModel
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class LoginState{
    IDLE,
    LOADING,
    ERROR,
    SUCCESS,
}



@HiltViewModel
class LoginStateViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : BaseViewModel<LoginAction>(){
    var user = UserModel(name = "", email = "")
    override fun doAction(action: LoginAction): Any = when(action) {
        is LoginAction.Login ->Login(action.code)
        is LoginAction.IsLoggedIn -> IsLoggedIn()
        is LoginAction.IsLoggedOut -> IsLoggedOut()
    }

    var uiState = mutableStateOf(LoginState.IDLE)
    var isRegistered = mutableStateOf<IsRegisteredResponse>(IsRegisteredResponse(false,"",0))
    var isLoggedIn = false
    private fun Login(code:String)=launch {
        uiState.value=LoginState.LOADING
        when(val res = loginRepository.Login(code)){
            is Result.Value-> {
                user = res.value
                isRegistered()

            }
            is Result.Error -> {
                mutableError.value = res.exception.message
                uiState.value=LoginState.ERROR
            }
        }
    }

    private fun IsLoggedIn() = launch {
        when(val res = loginRepository.isLoggedInCheck()){
            is edu.nitt.delta.orientation22.models.Result.Value -> isLoggedIn = res.value
            is edu.nitt.delta.orientation22.models.Result.Error -> mutableError.value = res.exception.message
        }
    }

    private fun IsLoggedOut() = launch {
        loginRepository.isLogOut()
    }
    private fun isRegistered() =launch {
        when(val res= loginRepository.isRegistered()){
            is edu.nitt.delta.orientation22.models.Result.Value -> {
                isRegistered.value=res.value
                uiState.value=LoginState.SUCCESS
            }
            is edu.nitt.delta.orientation22.models.Result.Error ->{
                mutableError.value = res.exception.message
                uiState.value=LoginState.ERROR
            }
        }
    }

}
