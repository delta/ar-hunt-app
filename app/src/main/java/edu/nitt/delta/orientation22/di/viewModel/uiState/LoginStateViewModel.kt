package edu.nitt.delta.orientation22.di.viewModel.uiState

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.nitt.delta.orientation22.di.viewModel.repository.LoginRepository
import javax.inject.Inject

@HiltViewModel
class LoginStateViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel(){

    fun log(){
        loginRepository.log()
    }

}
