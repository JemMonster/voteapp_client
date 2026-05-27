package com.example.voteapp.data.api

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponseDto(
    val code: String? = null,
    val error: String? = null,
)

