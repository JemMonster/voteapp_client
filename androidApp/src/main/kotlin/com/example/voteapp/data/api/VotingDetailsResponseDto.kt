package com.example.voteapp.data.api

import kotlinx.serialization.Serializable

@Serializable
data class VotingDetailsResponseDto(
    val id: String,
    val title: String,
    val description: String?,
    val type: VotingTypeDto,
    val status: VotingStatusDto,
    val imageUrl: String? = null,
    val startTime: String,
    val endTime: String,
    val totalVotes: Int = 0,
    val hasVoted: Boolean = false,
    val options: List<VotingOptionDto> = emptyList(),
)

@Serializable
data class VotingOptionDto(
    val id: String,
    val text: String,
)

