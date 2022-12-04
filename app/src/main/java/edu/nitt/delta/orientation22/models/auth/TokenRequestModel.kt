package edu.nitt.delta.orientation22.models.auth

import com.google.gson.annotations.SerializedName

open class TokenRequestModel(
    @SerializedName("user_token")
    val token : String
)
