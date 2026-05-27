package com.example.voteapp.domain.usecase

import com.example.voteapp.domain.model.Voting
import com.example.voteapp.domain.model.VotingOption
import com.example.voteapp.domain.model.VotingStatus
import com.example.voteapp.domain.model.VotingType
import com.example.voteapp.domain.port.VotingRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.Test

class GetVotingHistoryUseCaseTest {

    @Test
    fun `invoke -> calls repository getVotingHistory and returns list`() = kotlinx.coroutines.runBlocking {
        val repo = mockk<VotingRepository>()
        val useCase = GetVotingHistoryUseCase(repo)

        val mockHistory = listOf(
            Voting(
                id = "voting-1",
                title = "Past Voting 1",
                description = "Description 1",
                type = VotingType.SINGLE,
                status = VotingStatus.CLOSED,
                image = null,
                totalVotes = 25,
                endsAt = 1234567890L,
                hasVoted = true,
                options = listOf(
                    VotingOption(id = "1", text = "Option A", votes = 15),
                    VotingOption(id = "2", text = "Option B", votes = 10)
                )
            ),
            Voting(
                id = "voting-2",
                title = "Past Voting 2",
                description = "Description 2",
                type = VotingType.MULTIPLE,
                status = VotingStatus.CLOSED,
                image = null,
                totalVotes = 30,
                endsAt = 1234567800L,
                hasVoted = true,
                options = listOf(
                    VotingOption(id = "1", text = "Option X", votes = 20),
                    VotingOption(id = "2", text = "Option Y", votes = 18)
                )
            )
        )

        coEvery { repo.getVotingHistory() } returns mockHistory

        val result = useCase()

        require(result == mockHistory)
        require(result.size == 2)
        coVerify { repo.getVotingHistory() }
    }

    @Test
    fun `invoke -> returns empty list when no history`() = kotlinx.coroutines.runBlocking {
        val repo = mockk<VotingRepository>()
        val useCase = GetVotingHistoryUseCase(repo)

        coEvery { repo.getVotingHistory() } returns emptyList()

        val result = useCase()

        require(result.isEmpty())
        coVerify { repo.getVotingHistory() }
    }
}
