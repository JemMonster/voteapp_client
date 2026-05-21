package com.example.voteapp.domain.usecase

import com.example.voteapp.domain.model.Voting
import com.example.voteapp.domain.port.VotingRepository

class GetVotingsUseCase(
    private val repository: VotingRepository,
) {
    suspend operator fun invoke(): List<Voting> = repository.getVotings()
}


