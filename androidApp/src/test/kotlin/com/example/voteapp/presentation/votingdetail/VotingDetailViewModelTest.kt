package com.example.voteapp.presentation.votingdetail

import com.example.voteapp.domain.model.Voting
import com.example.voteapp.domain.model.VotingResult
import com.example.voteapp.domain.usecase.GetVotingDetailUseCase
import com.example.voteapp.domain.usecase.SubmitVoteUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class VotingDetailViewModelTest {

    private val dispatcher: TestDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `load success -> emits Success`() = runTest {
        val getVotingDetailUseCase = mockk<GetVotingDetailUseCase>()
        val submitVoteUseCase = mockk<SubmitVoteUseCase>(relaxed = true)

        val votingId = "1"
        val voting = Voting(
            id = votingId,
            title = "t",
            description = "d",
            type = com.example.voteapp.domain.model.VotingType.SINGLE,
            status = com.example.voteapp.domain.model.VotingStatus.ACTIVE,
            image = null,
            totalVotes = 0,
            endsAt = 0L,
            hasVoted = false,
            options = emptyList(),
        )

        coEvery { getVotingDetailUseCase(votingId) } returns voting

        val vm = VotingDetailViewModel(
            getVotingDetailUseCase = getVotingDetailUseCase,
            submitVoteUseCase = submitVoteUseCase,
        )

        vm.load(votingId)
        advanceUntilIdle()

        val state = vm.state.value
        require(state is VotingDetailState.Success)
        require(state.voting == voting)
    }

    @Test
    fun `submitVote already voted -> emits AlreadyVoted`() = runTest {
        val getVotingDetailUseCase = mockk<GetVotingDetailUseCase>(relaxed = true)
        val submitVoteUseCase = mockk<SubmitVoteUseCase>()

        val votingId = "1"
        val voting = Voting(
            id = votingId,
            title = "t",
            description = "d",
            type = com.example.voteapp.domain.model.VotingType.SINGLE,
            status = com.example.voteapp.domain.model.VotingStatus.ACTIVE,
            image = null,
            totalVotes = 0,
            endsAt = 0L,
            hasVoted = false,
            options = emptyList(),
        )

        // Init state manually by calling load success is fine, but easiest: set via load
        coEvery { getVotingDetailUseCase(votingId) } returns voting

        // submit throws already voted
        val err = RuntimeException("Already voted")
        coEvery { submitVoteUseCase(votingId, 1L, null) } throws err

        val vm = VotingDetailViewModel(
            getVotingDetailUseCase = getVotingDetailUseCase,
            submitVoteUseCase = submitVoteUseCase,
        )

        vm.load(votingId)
        advanceUntilIdle()

        vm.submitVote(votingId = votingId, optionId = 1L, optionIds = null)
        advanceUntilIdle()

        val state = vm.state.value
        require(state is VotingDetailState.AlreadyVoted)
        require(state.voting == voting)

        coVerify { submitVoteUseCase(votingId, 1L, null) }
    }

    @Test
    fun `submitVote success -> emits Success with lastVoteResult`() = runTest {
        val getVotingDetailUseCase = mockk<GetVotingDetailUseCase>(relaxed = true)
        val submitVoteUseCase = mockk<SubmitVoteUseCase>()

        val votingId = "1"
        val voting = Voting(
            id = votingId,
            title = "t",
            description = "d",
            type = com.example.voteapp.domain.model.VotingType.SINGLE,
            status = com.example.voteapp.domain.model.VotingStatus.ACTIVE,
            image = null,
            totalVotes = 0,
            endsAt = 0L,
            hasVoted = false,
            options = emptyList(),
        )

        val result = VotingResult(
            votingId = votingId.toLong(),
            status = com.example.voteapp.domain.model.VotingStatus.ACTIVE,
            type = com.example.voteapp.domain.model.VotingType.SINGLE,
            totalParticipants = 1,
            optionsResults = emptyList(),
            signaturesCount = null,
            winnerInfo = null,
        )

        coEvery { getVotingDetailUseCase(votingId) } returns voting
        coEvery { submitVoteUseCase(votingId, 1L, null) } returns result

        val vm = VotingDetailViewModel(
            getVotingDetailUseCase = getVotingDetailUseCase,
            submitVoteUseCase = submitVoteUseCase,
        )

        vm.load(votingId)
        advanceUntilIdle()

        vm.submitVote(votingId = votingId, optionId = 1L, optionIds = null)
        advanceUntilIdle()

        val state = vm.state.value
        require(state is VotingDetailState.Success)
        require(state.lastVoteResult == result)

        coVerify { submitVoteUseCase(votingId, 1L, null) }
    }
}

