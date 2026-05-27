package com.example.voteapp.data.repository

import com.example.voteapp.data.api.ApiService
import com.example.voteapp.data.mapper.toDomain
import com.example.voteapp.data.mapper.toDomainList
import com.example.voteapp.domain.port.VotingRepository
import javax.inject.Inject


class VotingRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : VotingRepository {
    
    override suspend fun getVotings(): List<com.example.voteapp.domain.model.Voting> {
        val response = apiService.getVotings()
        return response.items.map { it.toDomain() }
    }

    override suspend fun getVotingDetail(votingId: String): com.example.voteapp.domain.model.Voting {
        val response = apiService.getVotingDetail(votingId)
        return response.toDomain()
    }

    override suspend fun getVotingHistory(): List<com.example.voteapp.domain.model.Voting> {
        val response = apiService.getVotingHistory()
        return response.toDomainList()
    }

    override suspend fun submitVote(
        votingId: String,
        optionId: Long?,
        optionIds: List<Long>?,
    ): com.example.voteapp.domain.model.VotingResult {
        val response = apiService.submitVote(votingId, optionId, optionIds)
        return response.toDomain()
    }

    override suspend fun getVotingResults(votingId: String): com.example.voteapp.domain.model.VotingResult {
        val response = apiService.getVotingResults(votingId)
        return response.toDomain()
    }

    override suspend fun createVoting(
        title: String,
        description: String?,
        imageUrl: String?,
        type: com.example.voteapp.domain.model.VotingType,
        startTime: String,
        endTime: String,
        options: List<String>,
    ): String {
        val response = apiService.createVoting(
            title = title,
            description = description,
            imageUrl = imageUrl,
            type = type,
            startTime = startTime,
            endTime = endTime,
            options = options,
        )
        return response.toDomain()
    }

    override suspend fun inviteToVoting(votingId: String, email: String): String {
        val response = apiService.inviteToVoting(votingId, email)
        return response.toDomain()
    }
}


