package com.example.voteapp.domain.usecase

import com.example.voteapp.domain.model.Voting
import com.example.voteapp.domain.port.VotingRepository

class GetVotingDetailUseCase(
    private val repository: VotingRepository,
) {
    suspend operator fun invoke(votingId: String): Voting =
        repository.getVotingDetail(votingId)
}

