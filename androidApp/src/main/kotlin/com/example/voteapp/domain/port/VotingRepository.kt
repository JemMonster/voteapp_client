package com.example.voteapp.domain.port

import com.example.voteapp.domain.model.Voting

interface VotingRepository {
    suspend fun getVotings(): List<Voting>

    suspend fun getVotingDetail(votingId: String): Voting

    suspend fun getVotingHistory(): List<Voting>

    suspend fun submitVote(
        votingId: String,
        optionId: Long?,
        optionIds: List<Long>?,
    ): VotingResult

    suspend fun getVotingResults(votingId: String): VotingResult

    suspend fun createVoting(
        title: String,
        description: String?,
        imageUrl: String?,
        type: VotingType,
        startTime: String,
        endTime: String,
        options: List<String>,
    ): String

    suspend fun inviteToVoting(votingId: String, email: String): String
}


