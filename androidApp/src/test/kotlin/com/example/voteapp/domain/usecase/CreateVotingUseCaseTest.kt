package com.example.voteapp.domain.usecase

import com.example.voteapp.domain.model.VotingType
import com.example.voteapp.domain.port.VotingRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.Test

class CreateVotingUseCaseTest {

    @Test
    fun `invoke -> calls repository createVoting`() = kotlinx.coroutines.runBlocking {
        val repo = mockk<VotingRepository>()
        val useCase = CreateVotingUseCase(repo)

        coEvery {
            repo.createVoting(
                title = any(),
                description = any(),
                imageUrl = any(),
                type = any(),
                startTime = any(),
                endTime = any(),
                options = any(),
            )
        } returns "created"

        val result = useCase(
            title = "Title",
            description = "Desc",
            imageUrl = null,
            type = VotingType.SINGLE,
            startTime = "2020-01-01",
            endTime = "2020-01-02",
            options = listOf("A", "B"),
        )

        require(result == "created")
        coVerify { repo.createVoting(any(), any(), any(), any(), any(), any(), any()) }
    }
}

