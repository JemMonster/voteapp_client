package com.example.voteapp.data.api

import com.example.voteapp.domain.model.Voting
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.datetime.LocalDateTime
import javax.inject.Inject


class KtorApiService @Inject constructor(
    private val client: HttpClient,
) : ApiService {

    override suspend fun getVotings(): List<Voting> {
        val dtos: List<VotingDto> = client.get("api/v1/votings")
        return dtos.map { dto ->
            Voting(
                id = dto.id,
                title = dto.title,
                description = dto.description,
                type = dto.type,
                status = dto.status,
                image = dto.imageUrl,
                totalVotes = dto.totalVotes,
                endsAt = LocalDateTime.parse(dto.endsAt).toEpochMilliseconds(),
                hasVoted = dto.hasVoted,
                options = emptyList(),
            )
        }
    }

    override suspend fun getVotingDetail(votingId: String): Voting =
        throw NotImplementedError("KtorApiService is not wired for this endpoint. Use RetrofitApiService instead.")

    override suspend fun getVotingHistory(): List<Voting> =
        throw NotImplementedError("KtorApiService is not wired for this endpoint. Use RetrofitApiService instead.")

    override suspend fun submitVote(
        votingId: String,
        optionId: Long?,
        optionIds: List<Long>?,
    ): com.example.voteapp.domain.model.VotingResult =
        throw NotImplementedError("KtorApiService is not wired for this endpoint. Use RetrofitApiService instead.")

    override suspend fun getVotingResults(votingId: String): com.example.voteapp.domain.model.VotingResult =
        throw NotImplementedError("KtorApiService is not wired for this endpoint. Use RetrofitApiService instead.")

    override suspend fun createVoting(
        title: String,
        description: String?,
        imageUrl: String?,
        type: com.example.voteapp.domain.model.VotingType,
        startTime: String,
        endTime: String,
        options: List<String>,
    ): String =
        throw NotImplementedError("KtorApiService is not wired for this endpoint. Use RetrofitApiService instead.")

    override suspend fun inviteToVoting(votingId: String, email: String): String =
        throw NotImplementedError("KtorApiService is not wired for this endpoint. Use RetrofitApiService instead.")
}



