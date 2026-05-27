package com.example.voteapp.presentation.votingdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.voteapp.domain.model.Voting
import com.example.voteapp.domain.model.VotingResult
import com.example.voteapp.domain.usecase.GetVotingDetailUseCase
import com.example.voteapp.domain.usecase.GetVotingHistoryUseCase
import com.example.voteapp.domain.usecase.SubmitVoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface VotingDetailState {
    data object Loading : VotingDetailState
    data class Success(val voting: Voting, val lastVoteResult: VotingResult? = null) : VotingDetailState
    data class Error(val message: String) : VotingDetailState

    /** Сервер вернул, что пользователь уже проголосовал. */
    data class AlreadyVoted(val voting: Voting) : VotingDetailState
}

@HiltViewModel
class VotingDetailViewModel @Inject constructor(
    private val getVotingDetailUseCase: GetVotingDetailUseCase,
    private val submitVoteUseCase: SubmitVoteUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<VotingDetailState>(VotingDetailState.Loading)
    val state: StateFlow<VotingDetailState> = _state.asStateFlow()

    fun load(votingId: String) {
        viewModelScope.launch {
            _state.value = VotingDetailState.Loading
            runCatching {
                getVotingDetailUseCase(votingId)
            }.onSuccess { voting ->
                _state.value = VotingDetailState.Success(voting)
            }.onFailure { e ->
                _state.value = VotingDetailState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun submitVote(
        votingId: String,
        optionId: Long?,
        optionIds: List<Long>?,
    ) {
        val currentVoting = when (val s = _state.value) {
            is VotingDetailState.Success -> s.voting
            is VotingDetailState.AlreadyVoted -> s.voting
            else -> null
        } ?: return

        viewModelScope.launch {
            _state.value = VotingDetailState.Loading

            runCatching {
                submitVoteUseCase(votingId, optionId, optionIds)
            }.onSuccess { result ->
                _state.value = VotingDetailState.Success(currentVoting, lastVoteResult = result)
            }.onFailure { e ->
                // MVP: маппим already-voted по тексту сообщения.
                val msg = e.message.orEmpty().lowercase()
                if (msg.contains("already") || msg.contains("already voted") || msg.contains("уже")) {
                    _state.value = VotingDetailState.AlreadyVoted(currentVoting)
                } else {
                    _state.value = VotingDetailState.Error(e.message ?: "Unknown error")
                }
            }
        }
    }
}

