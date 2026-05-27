package com.example.voteapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class VotingResult(
    val votingId: String,
    val status: VotingStatus,
    val type: VotingType,
    val totalParticipants: Int,
    val optionsResults: List<OptionResult>? = null,
    val signaturesCount: Int? = null,
    val winnerInfo: WinnerInfo? = null,
    val isParticipating: Boolean? = null,
    val winnerOptionText: String? = null,
)

@Serializable
data class OptionResult(
    val optionId: String,
    val text: String,
    val percent: Double,
    val votesCount: Int,
)

@Serializable
data class WinnerInfo(
    val winnerUserId: String,
)

