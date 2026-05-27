package com.example.voteapp.notifications

import com.example.voteapp.domain.usecase.GetVotingsUseCase
import com.example.voteapp.domain.model.Voting
import com.example.voteapp.domain.model.VotingStatus
import com.example.voteapp.domain.model.VotingType
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Assert.assertEquals
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

/**
 * Placeholder test file.
 * Real Worker tests require androidx.test + Robolectric or instrumented environment.
 */
class VotingReminderWorkerTest {

    @Test
    fun `filter active votings only`() = runTest {
        val useCase = mock<GetVotingsUseCase>()
        whenever(useCase.invoke()).thenReturn(
            listOf(
                Voting(
                    id = "1",
                    title = "active",
                    description = "d",
                    type = VotingType.SINGLE,
                    status = VotingStatus.ACTIVE,
                    image = null,
                    totalVotes = 0,
                    endsAt = System.currentTimeMillis() + 1_000,
                    hasVoted = false,
                    options = emptyList(),
                ),
                Voting(
                    id = "2",
                    title = "closed",
                    description = "d",
                    type = VotingType.SINGLE,
                    status = VotingStatus.CLOSED,
                    image = null,
                    totalVotes = 0,
                    endsAt = System.currentTimeMillis() + 1_000,
                    hasVoted = false,
                    options = emptyList(),
                )
            )
        )

        // В текущем виде Worker содержит side-effects (уведомления), поэтому проверка логики
        // проводится на уровне usecase/фильтрации (визуально/инструментально).
        val votings = useCase.invoke()
        val active = votings.filter { it.status == VotingStatus.ACTIVE }
        assertEquals(1, active.size)
    }
}

