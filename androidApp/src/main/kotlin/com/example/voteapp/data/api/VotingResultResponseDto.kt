package com.example.voteapp.data.api

import com.example.voteapp.domain.model.VotingStatus
import com.example.voteapp.domain.model.VotingType
import kotlinx.serialization.Serializable

@Serializable
data class VotingResultResponseDto(
    val votingId: String,
    val status: VotingStatus,
    val type: VotingType,
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

