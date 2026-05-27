package com.example.voteapp.domain.usecase

import com.example.voteapp.domain.model.OptionResult
import com.example.voteapp.domain.model.VotingResult
import com.example.voteapp.domain.model.VotingStatus
import com.example.voteapp.domain.model.VotingType
import com.example.voteapp.domain.port.VotingRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.Test

class SubmitVoteUseCaseTest {

    @Test
    fun `invoke with optionId -> calls repository submitVote and returns result`() = kotlinx.coroutines.runBlocking {
        val repo = mockk<VotingRepository>()
        val useCase = SubmitVoteUseCase(repo)
        val votingId = "test-voting-id"
        val optionId = 1L

        val mockResult = VotingResult(
            votingId = votingId,
            status = VotingStatus.ACTIVE,
            type = VotingType.SINGLE,
            totalParticipants = 10,
            optionsResults = listOf(
                OptionResult(optionId = "1", text = "Option A", percent = 60.0, votesCount = 6),
                OptionResult(optionId = "2", text = "Option B", percent = 40.0, votesCount = 4)
            )
        )

        coEvery { repo.submitVote(votingId, optionId, null) } returns mockResult

        val result = useCase(votingId = votingId, optionId = optionId, optionIds = null)

        require(result == mockResult)
        coVerify { repo.submitVote(votingId, optionId, null) }
    }

    @Test
    fun `invoke with optionIds -> calls repository submitVote for multiple voting`() = kotlinx.coroutines.runBlocking {
        val repo = mockk<VotingRepository>()
        val useCase = SubmitVoteUseCase(repo)
        val votingId = "test-voting-id"
        val optionIds = listOf(1L, 2L)

        val mockResult = VotingResult(
            votingId = votingId,
            status = VotingStatus.ACTIVE,
            type = VotingType.MULTIPLE,
            totalParticipants = 5,
            optionsResults = listOf(
                OptionResult(optionId = "1", text = "Option A", percent = 80.0, votesCount = 4),
                OptionResult(optionId = "2", text = "Option B", percent = 60.0, votesCount = 3),
                OptionResult(optionId = "3", text = "Option C", percent = 20.0, votesCount = 1)
            )
        )

        coEvery { repo.submitVote(votingId, null, optionIds) } returns mockResult

        val result = useCase(votingId = votingId, optionId = null, optionIds = optionIds)

        require(result == mockResult)
        coVerify { repo.submitVote(votingId, null, optionIds) }
    }
}
