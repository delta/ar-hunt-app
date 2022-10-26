package edu.nitt.delta.orientation22.di.repository

import android.util.Log
import edu.nitt.delta.orientation22.di.api.ApiInterface
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val apiInterface: ApiInterface
) {


    fun log(){
        Log.v("INREPO","Hello")
    }

}