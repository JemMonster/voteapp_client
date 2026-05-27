package com.example.voteapp.data.api

import kotlinx.serialization.Serializable

@Serializable
data class VoteRequestDto(
    val selectedOptionIds: List<String> = emptyList(),
    val isParticipating: Boolean = true,
)

