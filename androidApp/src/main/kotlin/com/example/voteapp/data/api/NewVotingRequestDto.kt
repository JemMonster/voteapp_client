package com.example.voteapp.data.api

import com.example.voteapp.domain.model.VotingType
import kotlinx.serialization.Serializable

@Serializable
data class NewVotingRequestDto(
    val title: String,
    val description: String?,
    val imageUrl: String?,
    val votingType: VotingType,
    val startTime: String,
    val endTime: String,
    val options: List<String>,
)

