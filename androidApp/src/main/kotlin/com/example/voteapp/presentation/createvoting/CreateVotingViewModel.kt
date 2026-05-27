package com.example.voteapp.presentation.createvoting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.voteapp.domain.model.VotingType
import com.example.voteapp.domain.usecase.CreateVotingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface CreateVotingState {
    data object Idle : CreateVotingState
    data object Loading : CreateVotingState
    data class Error(val message: String) : CreateVotingState
    data object Success : CreateVotingState
}

@HiltViewModel
class CreateVotingViewModel @Inject constructor(
    private val createVotingUseCase: CreateVotingUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<CreateVotingState>(CreateVotingState.Idle)
    val state: StateFlow<CreateVotingState> = _state.asStateFlow()

    fun validateAndCreate(
        title: String,
        description: String?,
        type: VotingType,
        startTime: String,
        endTime: String,
        options: List<String>,
        imageUrl: String? = null,
    ) {
        val normalizedTitle = title.trim()
        val normalizedOptions = options.map { it.trim() }.filter { it.isNotBlank() }

        if (normalizedTitle.isBlank()) {
            _state.value = CreateVotingState.Error("Title is required")
            return
        }

        if (normalizedOptions.size < 2) {
            _state.value = CreateVotingState.Error("At least 2 options required")
            return
        }

        if (startTime.isBlank() || endTime.isBlank()) {
            _state.value = CreateVotingState.Error("Start/end time is required")
            return
        }

        _state.value = CreateVotingState.Loading

        viewModelScope.launch {
            runCatching {
                createVotingUseCase(
                    title = normalizedTitle,
                    description = description?.trim()?.takeIf { it.isNotBlank() },
                    imageUrl = imageUrl,
                    type = type,
                    startTime = startTime,
                    endTime = endTime,
                    options = normalizedOptions,
                )
            }.onSuccess {
                _state.value = CreateVotingState.Success
            }.onFailure { e ->
                _state.value = CreateVotingState.Error(e.message ?: "Unknown error")
            }
        }
    }
}

