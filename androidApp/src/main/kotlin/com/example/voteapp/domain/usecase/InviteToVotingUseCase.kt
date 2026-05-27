package com.example.voteapp.domain.usecase

import com.example.voteapp.domain.port.VotingRepository

class InviteToVotingUseCase(
    private val repository: VotingRepository,
) {
    suspend operator fun invoke(
        votingId: String,
        email: String,
    ): String = repository.inviteToVoting(
        votingId = votingId,
        email = email,
    )
}
