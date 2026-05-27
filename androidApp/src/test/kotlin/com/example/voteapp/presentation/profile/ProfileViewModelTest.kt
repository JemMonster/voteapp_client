package com.example.voteapp.presentation.profile

import com.google.firebase.auth.FirebaseAuth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
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
class ProfileViewModelTest {

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
    fun `signOut calls firebaseAuth signOut and invokes callback`() = runTest {
        val firebaseAuth = mockk<FirebaseAuth>()
        every { firebaseAuth.currentUser?.email } returns "user@test.com"
        every { firebaseAuth.signOut() } returns Unit

        val vm = ProfileViewModel(firebaseAuth)

        var done = false
        vm.signOut { done = true }

        advanceUntilIdle()

        verify { firebaseAuth.signOut() }
        require(done)
    }
}

