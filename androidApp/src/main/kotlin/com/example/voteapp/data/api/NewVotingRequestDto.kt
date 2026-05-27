package com.example.voteapp.data.api

import kotlinx.serialization.Serializable

@Serializable
data class NewVotingRequestDto(
    val title: String,
    val description: String?,
    val imageUrl: String?,
    val votingType: VotingTypeDto,
    val startTime: String,
    val endTime: String,
    val options: List<String>,
)

