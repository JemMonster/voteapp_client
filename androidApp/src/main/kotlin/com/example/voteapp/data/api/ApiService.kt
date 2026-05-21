package com.example.voteapp.data.api

import com.example.voteapp.domain.model.Voting

interface ApiService {
    suspend fun getVotings(): List<Voting>
}

@Deprecated(
    message = "Use VotingRepository port (domain) + GetVotingsUseCase instead. ApiService is an HTTP-level adapter.",
    replaceWith = ReplaceWith("getVotings")
)


