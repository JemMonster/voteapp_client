package com.example.voteapp.data.api

import kotlinx.serialization.Serializable

@Serializable
data class VotingDto(
    val id: String,
    val title: String,
    val description: String,
    val type: VotingTypeDto,
    val status: VotingStatusDto,
    val imageUrl: String? = null,
    val endsAt: String,
    val totalVotes: Int = 0,
    val hasVoted: Boolean = false,
)

