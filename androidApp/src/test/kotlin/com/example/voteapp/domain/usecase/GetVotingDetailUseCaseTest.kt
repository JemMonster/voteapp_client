package com.example.voteapp.domain.usecase

import com.example.voteapp.domain.model.Voting
import com.example.voteapp.domain.model.VotingOption
import com.example.voteapp.domain.model.VotingStatus
import com.example.voteapp.domain.model.VotingType
import com.example.voteapp.domain.port.VotingRepository
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.Test

class GetVotingDetailUseCaseTest {

    @Test
    fun `invoke -> calls repository getVotingDetail with votingId`() = kotlinx.coroutines.runBlocking {
        val repo = mockk<VotingRepository>()
        val useCase = GetVotingDetailUseCase(repo)
        val votingId = "test-id-123"

        val mockVoting = Voting(
            id = votingId,
            title = "Test Voting",
            description = "Test Description",
            type = VotingType.SINGLE,
            status = VotingStatus.ACTIVE,
            image = null,
            totalVotes = 0,
            endsAt = 1234567890L,
            hasVoted = false,
            options = listOf(
                VotingOption(id = "1", text = "Option A", votes = 5),
                VotingOption(id = "2", text = "Option B", votes = 3)
            )
        )

        // Mock не требуется для этой проверки, так как мы проверяем только вызов

        useCase(votingId = votingId)

        coVerify { repo.getVotingDetail(votingId) }
    }
}
