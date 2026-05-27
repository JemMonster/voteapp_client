package com.example.voteapp.data.api

import com.example.voteapp.data.api.VotingsResponseDto
import retrofit2.http.GET

interface RetrofitVotingsClient {
    @GET("api/v1/votings")
    suspend fun getVotings(): List<VotingsResponseDto>
}

