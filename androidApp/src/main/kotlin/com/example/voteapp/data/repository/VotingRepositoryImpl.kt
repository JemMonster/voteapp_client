package com.example.voteapp.data.repository

import com.example.voteapp.data.api.ApiService
import com.example.voteapp.domain.model.Voting
import com.example.voteapp.domain.port.VotingRepository
import javax.inject.Inject


class VotingRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : VotingRepository {
    override suspend fun getVotings(): List<Voting> = apiService.getVotings()
}

