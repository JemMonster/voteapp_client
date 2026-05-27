package com.example.voteapp.presentation.history

import com.example.voteapp.domain.model.Voting
import com.example.voteapp.domain.model.VotingStatus
import com.example.voteapp.domain.model.VotingType
import com.example.voteapp.domain.usecase.GetVotingHistoryUseCase
import io.mockk.coEvery
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
class VotingHistoryViewModelTest {

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
        val useCase = mockk<GetVotingHistoryUseCase>()
        val items = listOf(
            Voting(
                id = "1",
                title = "t",
                description = "d",
                type = VotingType.SINGLE,
                status = VotingStatus.ACTIVE,
                image = null,
                totalVotes = 0,
                endsAt = 0L,
                hasVoted = false,
                options = emptyList(),
            )
        )

        coEvery { useCase() } returns items

        val vm = VotingHistoryViewModel(useCase)
        advanceUntilIdle()

        val state = vm.state.value
        require(state is VotingHistoryState.Success)
        require(state.items == items)
    }

    @Test
    fun `load failure -> emits Error`() = runTest {
        val useCase = mockk<GetVotingHistoryUseCase>()
        coEvery { useCase() } throws RuntimeException("boom")

        val vm = VotingHistoryViewModel(useCase)
        advanceUntilIdle()

        val state = vm.state.value
        require(state is VotingHistoryState.Error)
        require(state.message.contains("boom", ignoreCase = true))
    }
}

