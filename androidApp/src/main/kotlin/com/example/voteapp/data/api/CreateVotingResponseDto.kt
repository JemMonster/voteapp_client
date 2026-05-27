package com.example.voteapp.data.api

import kotlinx.serialization.Serializable

@Serializable
data class CreateVotingResponseDto(
    val id: String,
    val message: String? = null,
)

