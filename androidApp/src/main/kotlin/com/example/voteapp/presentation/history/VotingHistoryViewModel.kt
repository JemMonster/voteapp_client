package com.example.voteapp.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.voteapp.domain.model.Voting
import com.example.voteapp.domain.usecase.GetVotingHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface VotingHistoryState {
    data object Loading : VotingHistoryState
    data class Success(val items: List<Voting>) : VotingHistoryState
    data class Error(val message: String) : VotingHistoryState
}

@HiltViewModel
class VotingHistoryViewModel @Inject constructor(
    private val getVotingHistoryUseCase: GetVotingHistoryUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<VotingHistoryState>(VotingHistoryState.Loading)
    val state: StateFlow<VotingHistoryState> = _state.asStateFlow()

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            _state.value = VotingHistoryState.Loading
            runCatching {
                getVotingHistoryUseCase()
            }.onSuccess { items ->
                _state.value = VotingHistoryState.Success(items)
            }.onFailure { e ->
                _state.value = VotingHistoryState.Error(e.message ?: "Unknown error")
            }
        }
    }
}

