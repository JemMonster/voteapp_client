package com.example.voteapp.data.api

import com.example.voteapp.data.api.VotingsResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitVotingsClient {
    @GET("api/v1/votings")
    suspend fun getVotings(): List<VotingsResponseDto>

    @GET("api/v1/votings/{id}")
    suspend fun getVotingDetail(
        @Path("id") id: String,
    ): VotingDetailsResponseDto

    @GET("api/v1/votings/history")
    suspend fun getVotingHistory(): VotingHistoryResponseDto

    @GET("api/v1/votings/{id}/results")
    suspend fun getVotingResults(
        @Path("id") id: String,
    ): VotingResultResponseDto

    @POST("api/v1/votings/{id}/vote")
    suspend fun submitVote(
        @Path("id") id: String,
        @Body payload: VoteRequestDto,
    ): VotingResultResponseDto

    @POST("api/v1/votings")
    suspend fun createVoting(
        @Body payload: NewVotingRequestDto,
    ): CreateVotingResponseDto

    @POST("api/v1/votings/{id}/invite")
    suspend fun invite(
        @Path("id") id: String,
        @Body payload: InviteRequestDto,
    ): CreateVotingResponseDto
}



