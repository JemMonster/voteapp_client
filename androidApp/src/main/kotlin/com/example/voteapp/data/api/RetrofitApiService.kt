package com.example.voteapp.data.api

import com.example.voteapp.domain.model.Voting
import io.ktor.util.InternalAPI
import kotlinx.datetime.LocalDateTime

/**
 * Retrofit-based HTTP adapter. Implemented to match ApiService contract.
 */
class RetrofitApiService(
    private val retrofitClient: RetrofitVotingsClient,
) : ApiService {

    override suspend fun getVotings(): List<Voting> {
        val dtos = retrofitClient.getVotings()
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
}

