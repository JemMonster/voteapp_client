package com.example.voteapp.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface ProfileState {
    data class Ready(val email: String?) : ProfileState
    data object SigningOut : ProfileState
    data class Error(val message: String) : ProfileState
}

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) : ViewModel() {

    private val _state = MutableStateFlow<ProfileState>(ProfileState.Ready(firebaseAuth.currentUser?.email))
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    fun refresh() {
        _state.value = ProfileState.Ready(firebaseAuth.currentUser?.email)
    }

    fun signOut(onDone: () -> Unit) {
        viewModelScope.launch {
            _state.value = ProfileState.SigningOut
            firebaseAuth.signOut()
            _state.value = ProfileState.Ready(firebaseAuth.currentUser?.email)
            onDone()
        }
    }

    fun sendPasswordResetEmail(onDone: () -> Unit) {
        val email = firebaseAuth.currentUser?.email
        if (email.isNullOrBlank()) {
            _state.value = ProfileState.Error("Email is not available")
            return
        }

        viewModelScope.launch {
            runCatching {
                firebaseAuth.sendPasswordResetEmail(email).await()
            }.onSuccess {
                onDone()
                refresh()
            }.onFailure { e ->
                _state.value = ProfileState.Error(e.message ?: "Unknown error")
            }
        }
    }
}

