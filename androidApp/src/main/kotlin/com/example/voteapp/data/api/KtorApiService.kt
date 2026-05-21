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
}


