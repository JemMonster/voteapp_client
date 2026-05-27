package com.example.voteapp.domain.usecase

import com.example.voteapp.domain.model.VotingType
import com.example.voteapp.domain.port.VotingRepository

class CreateVotingUseCase(
    private val repository: VotingRepository,
) {
    suspend operator fun invoke(
        title: String,
        description: String?,
        imageUrl: String?,
        type: VotingType,
        startTime: String,
        endTime: String,
        options: List<String>,
    ): String = repository.createVoting(
        title = title,
        description = description,
        imageUrl = imageUrl,
        type = type,
        startTime = startTime,
        endTime = endTime,
        options = options,
    )
}

