package com.example.voteapp.data.api

import kotlinx.serialization.Serializable

@Serializable
data class InviteRequestDto(
    val email: String,
)

