package com.example.voteapp.domain.port

import com.example.voteapp.domain.model.Voting

interface VotingRepository {
    suspend fun getVotings(): List<Voting>
}

