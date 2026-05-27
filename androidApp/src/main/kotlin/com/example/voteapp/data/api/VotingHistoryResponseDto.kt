package com.example.voteapp.data.api

import kotlinx.serialization.Serializable

@Serializable
data class VotingHistoryResponseDto(
    val items: List<VotingDetailsResponseDto>,
)

