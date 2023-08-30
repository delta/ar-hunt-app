package edu.nitt.delta.orientation22.models.game

import com.google.gson.annotations.SerializedName

open class PostAnswerRequest(
    @SerializedName("user_token")
    val token : String,
    @SerializedName("current_level")
    val currentLevel: Int,
)