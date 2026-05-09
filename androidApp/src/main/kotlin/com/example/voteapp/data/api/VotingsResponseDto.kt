package com.example.voteapp.data.api

import kotlinx.serialization.Serializable

@Serializable
data class VotingsResponseDto(
    val items: List<VotingDto>
)

