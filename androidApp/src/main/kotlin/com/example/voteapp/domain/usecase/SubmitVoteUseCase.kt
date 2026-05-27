package com.example.voteapp.domain.usecase

import com.example.voteapp.domain.model.VotingResult
import com.example.voteapp.domain.port.VotingRepository

class SubmitVoteUseCase(
    private val repository: VotingRepository,
) {
    suspend operator fun invoke(
        votingId: String,
        optionId: Long?,
        optionIds: List<Long>?,
    ): VotingResult =
        repository.submitVote(votingId, optionId, optionIds)
}

