package com.example.voteapp.domain.model

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

enum class VotingType {
    SINGLE, MULTIPLE, PETITION, RAFFLE
}

enum class VotingStatus {
    ACTIVE, CLOSED
}

data class VotingOption(
    val id: String,
    val text: String,
    val votes: Int
)

