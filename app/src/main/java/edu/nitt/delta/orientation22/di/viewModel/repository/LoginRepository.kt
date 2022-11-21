package edu.nitt.delta.orientation22.di.viewModel.repository

import android.util.Log
import edu.nitt.delta.orientation22.di.api.ApiInterface
import edu.nitt.delta.orientation22.di.api.ResponseConstants
import edu.nitt.delta.orientation22.di.storage.SharedPrefHelper
import edu.nitt.delta.orientation22.models.Result
import edu.nitt.delta.orientation22.models.auth.LoginResponse
import edu.nitt.delta.orientation22.models.auth.UserModel
import edu.nitt.delta.orientation22.models.leaderboard.LeaderboardData
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val apiInterface: ApiInterface
) {
    @Inject
    lateinit var sharedPrefHelper: SharedPrefHelper
    suspend fun Login(code:String): Result<UserModel> =try {
        val response = apiInterface.login(code)
        Log.d("Login",response.user_token)
        if(response.user_token != "" )
        {
            sharedPrefHelper.token = response.user_token
            Result.build { UserModel(name = response.name, email = response.email) }
        } else {
            Log.d("Login", "Out")
            Result.build { throw Exception(ResponseConstants.ERROR) }
        }
    }catch (e:Exception){
        Log.d("Login",e.message.toString())
        Result.build { throw e }
    }

    fun isLoggedInCheck():edu.nitt.delta.orientation22.models.Result<Boolean> =try {
        val token = sharedPrefHelper.token
        Log.d("sharedLogin",token.toString())
        if(token == "")
            edu.nitt.delta.orientation22.models.Result.build { false }
        else
            edu.nitt.delta.orientation22.models.Result.build { true }

    }catch (e:Exception){
        edu.nitt.delta.orientation22.models.Result.build { throw e }
    }

    fun isLogOut(){
        sharedPrefHelper.clear()
    }
}
