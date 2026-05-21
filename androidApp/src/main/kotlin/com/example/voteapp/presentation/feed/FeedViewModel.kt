package com.example.voteapp.presentation.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.voteapp.domain.model.Voting
import com.example.voteapp.domain.usecase.GetVotingsUseCase

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val getVotingsUseCase: GetVotingsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<FeedState>(FeedState.Loading)
    val state: StateFlow<FeedState> = _state.asStateFlow()

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            _state.value = FeedState.Loading
            runCatching {
                getVotingsUseCase()
            }.onSuccess { votings ->
                _state.value = FeedState.Success(votings)
            }.onFailure {
                _state.value = FeedState.Error(it.message ?: "Unknown error")
            }
        }
    }
}

sealed interface FeedState {
    data object Loading : FeedState
    data class Success(val votings: List<Voting>) : FeedState
    data class Error(val message: String) : FeedState
}

