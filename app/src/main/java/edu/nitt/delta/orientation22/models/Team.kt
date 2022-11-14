package edu.nitt.delta.orientation22.models

data class Team(
    val teamName: String,
    val leader: TeamMember,
    val members: MutableList<TeamMember>,
    val points: Int = 0
)
