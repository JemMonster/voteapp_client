package com.example.voteapp.data.repository

import com.example.voteapp.data.api.ApiService
import com.example.voteapp.domain.model.Voting
import com.example.voteapp.domain.model.VotingResult
import com.example.voteapp.domain.model.VotingType

import com.example.voteapp.domain.port.VotingRepository
import javax.inject.Inject


class VotingRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : VotingRepository {
    override suspend fun getVotings(): List<Voting> = apiService.getVotings()

    override suspend fun getVotingDetail(votingId: String): Voting =
        apiService.getVotingDetail(votingId)

    override suspend fun getVotingHistory(): List<Voting> =
        apiService.getVotingHistory()

    override suspend fun submitVote(
        votingId: String,
        optionId: Long?,
        optionIds: List<Long>?,
    ): VotingResult =
        apiService.submitVote(votingId = votingId, optionId = optionId, optionIds = optionIds)

    override suspend fun getVotingResults(votingId: String): VotingResult =
        apiService.getVotingResults(votingId)

    override suspend fun createVoting(
        title: String,
        description: String?,
        imageUrl: String?,
        type: VotingType,
        startTime: String,
        endTime: String,
        options: List<String>,
    ): String =
        apiService.createVoting(
            title = title,
            description = description,
            imageUrl = imageUrl,
            type = type,
            startTime = startTime,
            endTime = endTime,
            options = options,
        )

    override suspend fun inviteToVoting(votingId: String, email: String): String =
        apiService.inviteToVoting(votingId, email)
}


