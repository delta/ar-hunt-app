package edu.nitt.delta.orientation22.models.auth

import com.google.gson.annotations.SerializedName
import edu.nitt.delta.orientation22.models.BaseResponse

data class LoginResponse(
    @SerializedName("email")
    val email:String,
    @SerializedName("name")
    val name : String,
    @SerializedName("token")
    val user_token : String
):BaseResponse<String>()