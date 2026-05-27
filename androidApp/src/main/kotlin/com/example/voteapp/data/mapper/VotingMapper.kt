package com.example.voteapp.data.mapper

import com.example.voteapp.data.api.VotingDto
import com.example.voteapp.data.api.VotingsResponseDto
import com.example.voteapp.data.api.VotingDetailsResponseDto
import com.example.voteapp.data.api.VotingHistoryResponseDto
import com.example.voteapp.data.api.VotingResultResponseDto
import com.example.voteapp.data.api.OptionResultDto
import com.example.voteapp.data.api.CreateVotingResponseDto
import com.example.voteapp.data.api.WinnerInfoDto
import com.example.voteapp.domain.model.Voting
import com.example.voteapp.domain.model.VotingOption
import com.example.voteapp.domain.model.VotingResult
import com.example.voteapp.domain.model.OptionResult
import com.example.voteapp.domain.model.WinnerInfo

/**
 * Mapper: DTO -> Domain Model
 */
fun VotingDto.toDomain(): Voting {
    return Voting(
        id = this.id,
        title = this.title,
        description = this.description,
        type = this.type.toDomain(),
        status = this.status.toDomain(),
        image = this.imageUrl,
        totalVotes = this.totalVotes,
        endsAt = this.endsAt.toLongOrNull() ?: 0L,
        hasVoted = this.hasVoted,
        options = emptyList() // Options are not available in list view
    )
}

fun VotingDetailsResponseDto.toDomain(): Voting {
    return Voting(
        id = this.id,
        title = this.title,
        description = this.description ?: "",
        type = this.type.toDomain(),
        status = this.status.toDomain(),
        image = this.imageUrl,
        totalVotes = this.totalVotes,
        endsAt = this.endTime.toLongOrNull() ?: 0L,
        hasVoted = this.hasVoted,
        options = this.options?.map { it.toDomain() } ?: emptyList()
    )
}

fun VotingHistoryResponseDto.toDomainList(): List<Voting> {
    return this.items.map { it.toDomain() }
}

private fun com.example.voteapp.data.api.VotingOptionDto.toDomain(): VotingOption {
    return VotingOption(
        id = this.id,
        text = this.text,
        votes = 0 // Votes count will be updated when results are fetched
    )
}

fun VotingResultResponseDto.toDomain(): VotingResult {
    return VotingResult(
        votingId = this.votingId,
        status = this.status.toDomain(),
        type = this.type.toDomain(),
        totalParticipants = this.totalParticipants,
        optionsResults = this.optionsResults?.map { it.toDomain() } ?: emptyList(),
        signaturesCount = this.signaturesCount,
        winnerInfo = this.winnerInfo?.toDomain(),
        isParticipating = this.isParticipating ?: false,
        winnerOptionText = this.winnerOptionText
    )
}

private fun OptionResultDto.toDomain(): OptionResult {
    return OptionResult(
        optionId = this.optionId,
        text = this.text,
        percent = this.percent,
        votesCount = this.votesCount
    )
}

private fun WinnerInfoDto.toDomain(): WinnerInfo {
    return WinnerInfo(
        winnerUserId = this.winnerUserId
    )
}

fun CreateVotingResponseDto.toDomain(): String {
    return this.id
}
