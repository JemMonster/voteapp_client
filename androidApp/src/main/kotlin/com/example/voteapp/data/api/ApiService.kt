package com.example.voteapp.data.api

import com.example.voteapp.domain.model.Voting

interface ApiService {
    suspend fun getVotings(): List<Voting>
}

