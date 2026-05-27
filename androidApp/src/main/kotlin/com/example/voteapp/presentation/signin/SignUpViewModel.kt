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

data class SignUpUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false,
)

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: EmailPasswordAuthRepository,
    private val firebaseAuth: FirebaseAuth,
) : ViewModel() {

    private val _state = MutableStateFlow(SignUpUiState())
    val state: StateFlow<SignUpUiState> = _state.asStateFlow()

    fun onEmailChanged(value: String) {
        _state.value = _state.value.copy(email = value, errorMessage = null)
    }

    fun onPasswordChanged(value: String) {
        _state.value = _state.value.copy(password = value, errorMessage = null)
    }

    fun signUp() {
        val current = _state.value
        if (current.isLoading) return

        viewModelScope.launch {
            _state.value = current.copy(isLoading = true, errorMessage = null, isSuccess = false)

            runCatching {
                authRepository.signUp(current.email.trim(), current.password)
            }.onSuccess {
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

