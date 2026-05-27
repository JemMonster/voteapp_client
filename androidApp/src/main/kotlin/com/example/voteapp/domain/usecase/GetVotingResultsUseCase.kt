package com.example.voteapp.domain.usecase

import com.example.voteapp.domain.model.VotingResult
import com.example.voteapp.domain.port.VotingRepository

class GetVotingResultsUseCase(
    private val repository: VotingRepository,
) {
    suspend operator fun invoke(
        votingId: String,
    ): VotingResult = repository.getVotingResults(votingId)
}
