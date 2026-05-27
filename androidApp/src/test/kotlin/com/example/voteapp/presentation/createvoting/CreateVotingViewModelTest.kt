package com.example.voteapp.presentation.createvoting

import com.example.voteapp.domain.model.VotingType
import com.example.voteapp.domain.usecase.CreateVotingUseCase
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
class CreateVotingViewModelTest {

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
    fun `validate empty title -> Error`() = runTest {
        val useCase = mockk<CreateVotingUseCase>(relaxed = true)
        val vm = CreateVotingViewModel(createVotingUseCase = useCase)

        vm.validateAndCreate(
            title = "  ",
            description = null,
            type = VotingType.SINGLE,
            startTime = "2020-01-01",
            endTime = "2020-01-02",
            options = listOf("A", "B"),
        )

        val state = vm.state.value
        require(state is CreateVotingState.Error)
    }

    @Test
    fun `validate less than 2 options -> Error`() = runTest {
        val useCase = mockk<CreateVotingUseCase>(relaxed = true)
        val vm = CreateVotingViewModel(createVotingUseCase = useCase)

        vm.validateAndCreate(
            title = "Title",
            description = null,
            type = VotingType.SINGLE,
            startTime = "2020-01-01",
            endTime = "2020-01-02",
            options = listOf("A"),
        )

        val state = vm.state.value
        require(state is CreateVotingState.Error)
    }

    @Test
    fun `success -> emits Success`() = runTest {
        val useCase = mockk<CreateVotingUseCase>()
        coEvery {
            useCase(
                title = any(),
                description = any(),
                imageUrl = any(),
                type = any(),
                startTime = any(),
                endTime = any(),
                options = any(),
            )
        } returns "created"

        val vm = CreateVotingViewModel(createVotingUseCase = useCase)

        vm.validateAndCreate(
            title = "Title",
            description = "Desc",
            type = VotingType.SINGLE,
            startTime = "2020-01-01",
            endTime = "2020-01-02",
            options = listOf("A", "B"),
        )
        advanceUntilIdle()

        val state = vm.state.value
        require(state is CreateVotingState.Success)

        coVerify { useCase(any(), any(), any(), any(), any(), any(), any()) }
    }
+}

