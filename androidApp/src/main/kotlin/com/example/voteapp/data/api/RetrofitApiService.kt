package com.example.voteapp.data.api

import com.example.voteapp.data.mapper.toDomain
import com.example.voteapp.data.mapper.toDomainList

/**
 * Retrofit-based HTTP adapter. Returns DTOs - mapping to domain happens in repository.
 */
class RetrofitApiService(
    private val retrofitClient: RetrofitVotingsClient,
) : ApiService {

    override suspend fun getVotings(): VotingsResponseDto {
        return retrofitClient.getVotings().firstOrNull() 
            ?: VotingsResponseDto(items = emptyList())
    }

    override suspend fun getVotingDetail(votingId: String): VotingDetailsResponseDto {
        return retrofitClient.getVotingDetail(votingId)
    }

    override suspend fun getVotingHistory(): VotingHistoryResponseDto {
        return retrofitClient.getVotingHistory()
    }

    override suspend fun submitVote(
        votingId: String,
        optionId: Long?,
        optionIds: List<Long>?,
    ): VotingResultResponseDto {
        return retrofitClient.submitVote(
            id = votingId,
            payload = VoteRequestDto(
                selectedOptionIds = optionIds?.map { it.toString() } ?: (optionId?.let { listOf(it.toString()) } ?: emptyList())
            )
        )
    }

    override suspend fun getVotingResults(votingId: String): VotingResultResponseDto {
        return retrofitClient.getVotingResults(votingId)
    }

    override suspend fun createVoting(
        title: String,
        description: String?,
        imageUrl: String?,
        type: com.example.voteapp.domain.model.VotingType,
        startTime: String,
        endTime: String,
        options: List<String>,
    ): CreateVotingResponseDto {
        return retrofitClient.createVoting(
            payload = NewVotingRequestDto(
                title = title,
                description = description,
                imageUrl = imageUrl,
                votingType = VotingTypeDto.fromDomain(type),
                startTime = startTime,
                endTime = endTime,
                options = options,
            )
        )
    }

    override suspend fun inviteToVoting(votingId: String, email: String): CreateVotingResponseDto {
        return retrofitClient.invite(
            id = votingId,
            payload = InviteRequestDto(email = email),
        )
    }
}


