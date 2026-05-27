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
        val dtos = retrofitClient.getVotings().flatMap { it.items }
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

    override suspend fun getVotingDetail(votingId: String): Voting {
        val dto = retrofitClient.getVotingDetail(votingId)
        return Voting(
            id = dto.id,
            title = dto.title,
            description = dto.description.orEmpty(),
            type = dto.type,
            status = dto.status,
            image = dto.imageUrl,
            totalVotes = dto.totalVotes,
            endsAt = LocalDateTime.parse(dto.endTime).toEpochMilliseconds(),
            hasVoted = dto.hasVoted,
            options = dto.options.map { o ->
                VotingOption(
                    id = o.id,
                    text = o.text,
                    votes = 0,
                )
            },
        )
    }

    override suspend fun getVotingHistory(): List<Voting> {
        val dto = retrofitClient.getVotingHistory()
        return dto.items.map { item ->
            Voting(
                id = item.id,
                title = item.title,
                description = item.description.orEmpty(),
                type = item.type,
                status = item.status,
                image = item.imageUrl,
                totalVotes = item.totalVotes,
                endsAt = LocalDateTime.parse(item.endTime).toEpochMilliseconds(),
                hasVoted = item.hasVoted,
                options = item.options.map { o ->
                    VotingOption(id = o.id, text = o.text, votes = 0)
                },
            )
        }
    }

    override suspend fun submitVote(
        votingId: String,
        optionId: Long?,
        optionIds: List<Long>?,
    ): VotingResult {
        val resp = retrofitClient.submitVote(
            id = votingId,
            payload = VoteRequestDto(optionId = optionId, optionIds = optionIds),
        )
        return resp.toDomain()
    }

    override suspend fun getVotingResults(votingId: String): VotingResult {
        val resp = retrofitClient.getVotingResults(votingId)
        return resp.toDomain()
    }

    override suspend fun createVoting(
        title: String,
        description: String?,
        imageUrl: String?,
        type: VotingType,
        startTime: String,
        endTime: String,
        options: List<String>,
    ): String {
        val resp = retrofitClient.createVoting(
            payload = NewVotingRequestDto(
                title = title,
                description = description,
                imageUrl = imageUrl,
                votingType = type,
                startTime = startTime,
                endTime = endTime,
                options = options,
            )
        )
        return resp.id
    }

    override suspend fun inviteToVoting(votingId: String, email: String): String {
        val resp = retrofitClient.invite(
            id = votingId,
            payload = InviteRequestDto(email = email),
        )
        return resp.id
    }
}

private fun VotingResultResponseDto.toDomain(): VotingResult {
    return VotingResult(
        votingId = votingId,
        status = status,
        type = type,
        totalParticipants = totalParticipants,
        optionsResults = optionsResults?.map { o ->
            OptionResult(optionId = o.optionId, text = o.text, percent = o.percent, votesCount = o.votesCount)
        },
        signaturesCount = signaturesCount,
        winnerInfo = winnerInfo?.let { w -> WinnerInfo(winnerUserId = w.winnerUserId) },
        isParticipating = isParticipating,
        winnerOptionText = winnerOptionText,
    )
}


