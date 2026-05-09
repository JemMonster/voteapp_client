package com.example.voteapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Voting(
    val id: String,
    val title: String,
    val description: String,
    val type: VotingType,
    val status: VotingStatus,
    val image: String? = null,
    val totalVotes: Int = 0,
    val endsAt: Long, // timestamp
    val hasVoted: Boolean = false,
    val options: List<VotingOption> = emptyList()
)

@Serializable
enum class VotingType {
    SINGLE, MULTIPLE, PETITION, RAFFLE
}

@Serializable
enum class VotingStatus {
    ACTIVE, CLOSED
}

data class VotingOption(
    val id: String,
    val text: String,
    val votes: Int,
)


