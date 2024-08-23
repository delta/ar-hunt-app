package edu.nitt.delta.orientation22.models.game

import com.google.gson.annotations.SerializedName

data class CheckAnswerRequest (
    @SerializedName("answer")
    val answer: String
)