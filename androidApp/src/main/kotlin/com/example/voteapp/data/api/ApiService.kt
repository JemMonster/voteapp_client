package com.example.voteapp.data.api

/**
 * Data layer API interface - works with DTOs only.
 * Domain models are returned after mapping in the repository.
 * 
 * Dependency Rule: Data layer depends on domain for use-cases,
 * but API interface should only expose DTOs to avoid leaking
 * domain models into the data layer.
 */
interface ApiService {
    suspend fun getVotings(): VotingsResponseDto

    suspend fun getVotingDetail(votingId: String): VotingDetailsResponseDto

    suspend fun getVotingHistory(): VotingHistoryResponseDto

    suspend fun submitVote(votingId: String, optionId: Long?, optionIds: List<Long>?): VotingResultResponseDto

    suspend fun getVotingResults(votingId: String): VotingResultResponseDto

    suspend fun createVoting(
        title: String,
        description: String?,
        imageUrl: String?,
        type: com.example.voteapp.domain.model.VotingType,
        startTime: String,
        endTime: String,
        options: List<String>,
    ): CreateVotingResponseDto

    suspend fun inviteToVoting(votingId: String, email: String): CreateVotingResponseDto
}


