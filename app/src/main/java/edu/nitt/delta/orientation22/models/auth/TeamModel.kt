package edu.nitt.delta.orientation22.models.auth

data class TeamModel(
    val teamName: String,
    val members: List<Member>,
    val points: Int = 0,
    val avatar : Int
)
