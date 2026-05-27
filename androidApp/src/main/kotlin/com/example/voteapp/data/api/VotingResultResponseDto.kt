package com.example.voteapp.data.api

import kotlinx.serialization.Serializable

@Serializable
data class VotingResultResponseDto(
    val votingId: String,
    val status: VotingStatusDto,
    val type: VotingTypeDto,
    val totalParticipants: Int,
    val optionsResults: List<OptionResultDto>? = null,
    val signaturesCount: Int? = null,
    val winnerInfo: WinnerInfoDto? = null,
    val isParticipating: Boolean? = null,
    val winnerOptionText: String? = null,
)

@Serializable
data class OptionResultDto(
    val optionId: String,
    val text: String,
    val percent: Double,
    val votesCount: Int,
)

@Serializable
data class WinnerInfoDto(
    val winnerUserId: String,
)

