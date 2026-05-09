package com.example.voteapp.data.api

import com.example.voteapp.domain.model.VotingStatus
import com.example.voteapp.domain.model.VotingType
import kotlinx.serialization.Serializable

@Serializable
data class VotingDto(
    val id: String,
    val title: String,
    val description: String,
    val type: VotingType,
    val status: VotingStatus,
    val imageUrl: String? = null,
    val endsAt: String,
    val totalVotes: Int = 0,
    val hasVoted: Boolean = false,
)

