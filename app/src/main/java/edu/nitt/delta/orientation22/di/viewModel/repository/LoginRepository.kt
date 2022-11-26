package edu.nitt.delta.orientation22.di.viewModel.repository

import android.util.Log
import edu.nitt.delta.orientation22.di.api.ApiInterface
import edu.nitt.delta.orientation22.di.api.ResponseConstants
import edu.nitt.delta.orientation22.di.storage.SharedPrefHelper
import edu.nitt.delta.orientation22.models.IsRegisteredResponse
import edu.nitt.delta.orientation22.models.Result
import edu.nitt.delta.orientation22.models.auth.TokenRequestModel
import edu.nitt.delta.orientation22.models.auth.UserModel
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
            sharedPrefHelper.leaderName =response.name
            sharedPrefHelper.rollNo = response.email.substring(0,9).toInt()
            Result.build { UserModel(name = response.name, email = response.email) }
        } else {
            Log.d("Login", response.message.toString())
            Result.build { throw Exception(ResponseConstants.ERROR) }
        }
    }catch (e:Exception){
        Log.d("Login",e.message.toString())
        Result.build { throw Exception(ResponseConstants.ERROR) }
    }

    fun isLoggedInCheck():Result<Boolean> =try {
        val token = sharedPrefHelper.token
        Log.d("sharedLogin",token.toString())
        if(token == "")
            Result.build { false }
        else
            Result.build { true }

    }catch (e:Exception){
        Result.build { throw e }
    }

    suspend fun isRegistered():Result<IsRegisteredResponse> =try {
        val token = sharedPrefHelper.token
        val response = apiInterface.isRegistered(TokenRequestModel(token.toString()))
        Log.d("isRegistered",response.toString())
        if(response.message == ResponseConstants.SUCCESS){
            Log.d("isRegistered",response.toString())
            Result.build { response }
        } else{
            Log.d("isRegistered",response.message.toString())
            Result.build { response }
        }

    }catch (e:Exception){
        Log.d("isRegistered",e.message.toString())
        Result.build { throw Exception(ResponseConstants.ERROR) }
    }

    fun isLogOut(){
        sharedPrefHelper.clear()
    }

    suspend fun isLive() : Result<Boolean> = try {
        val token = sharedPrefHelper.token.toString()
        val response = apiInterface.isLive(TokenRequestModel(token))
        Log.d("isLive",response.live.toString())
        if (response.message == ResponseConstants.SUCCESS){
            Result.build { response.live }
        }
        else{
            Result.build { false }
        }
    }catch (e:Exception){
        Result.build { throw e }
    }
}
