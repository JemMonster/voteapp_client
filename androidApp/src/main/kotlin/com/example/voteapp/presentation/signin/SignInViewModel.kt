package com.example.voteapp.presentation.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.voteapp.domain.auth.EmailPasswordAuthRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SignInUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false,
)

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: EmailPasswordAuthRepository,
    private val firebaseAuth: FirebaseAuth,
) : ViewModel() {

    private val _state = MutableStateFlow(SignInUiState())
    val state: StateFlow<SignInUiState> = _state.asStateFlow()

    fun onEmailChanged(value: String) {
        _state.value = _state.value.copy(email = value, errorMessage = null)
    }

    fun onPasswordChanged(value: String) {
        _state.value = _state.value.copy(password = value, errorMessage = null)
    }

    fun signIn() {
        val current = _state.value
        if (current.isLoading) return

        viewModelScope.launch {
            _state.value = current.copy(isLoading = true, errorMessage = null, isSuccess = false)

            runCatching {
                authRepository.signIn(current.email.trim(), current.password)
            }.onSuccess {
                // Firebase current user is already set by FirebaseAuth.
                // Token interceptor will pick it up automatically on next requests.
                _state.value = _state.value.copy(isLoading = false, isSuccess = true)
            }.onFailure { e ->
                _state.value = _state.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Unknown error",
                    isSuccess = false,
                )
            }
        }
    }

    fun resetSuccess() {
        _state.value = _state.value.copy(isSuccess = false)
    }
}

