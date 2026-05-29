package com.example.voteapp.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.voteapp.domain.usecase.GetVotingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SearchUiState(
    val query: String = "",
    val searchState: SearchState = SearchState.Initial,
    val history: List<String> = emptyList(),
    val isHistoryVisible: Boolean = false
)

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getVotingsUseCase: GetVotingsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private var lastSearchQuery: String = ""

    fun onQueryChanged(query: String) {
        _uiState.value = _uiState.value.copy(query = query)
    }

    fun showHistory() {
        _uiState.value = _uiState.value.copy(isHistoryVisible = true)
    }

    fun hideHistory() {
        _uiState.value = _uiState.value.copy(isHistoryVisible = false)
    }

    fun clearQuery() {
        _uiState.value = _uiState.value.copy(
            query = "",
            isHistoryVisible = false
        )
    }

    fun performSearch(query: String) {
        if (query.isBlank()) return

        lastSearchQuery = query
        _uiState.value = _uiState.value.copy(
            searchState = SearchState.Loading,
            isHistoryVisible = false
        )

        viewModelScope.launch {
            try {
                val votings = getVotingsUseCase()
                val filtered = votings.filter {
                    it.title.contains(query, ignoreCase = true) ||
                    it.description?.contains(query, ignoreCase = true) == true
                }

                if (filtered.isEmpty()) {
                    _uiState.value = _uiState.value.copy(
                        searchState = SearchState.NoResults
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        searchState = SearchState.Success(
                            results = filtered.map { voting ->
                                VotingSearchResult(
                                    id = voting.id,
                                    title = voting.title,
                                    description = voting.description,
                                    type = voting.type.name,
                                    status = voting.status.name
                                )
                            }
                        )
                    )
                    addToHistory(query)
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    searchState = SearchState.Error(
                        message = e.message ?: "Ошибка поиска",
                        lastQuery = query
                    )
                )
            }
        }
    }

    fun retrySearch() {
        if (lastSearchQuery.isNotBlank()) {
            performSearch(lastSearchQuery)
        }
    }

    fun selectFromHistory(query: String) {
        onQueryChanged(query)
        performSearch(query)
    }

    fun clearHistory() {
        _uiState.value = _uiState.value.copy(
            history = emptyList(),
            isHistoryVisible = false
        )
    }

    private fun addToHistory(query: String) {
        val currentHistory = _uiState.value.history.toMutableList()
        
        // Remove if already exists
        currentHistory.removeAll { it.equals(query, ignoreCase = true) }
        
        // Add to beginning
        currentHistory.add(0, query)
        
        // Keep only 10 items
        if (currentHistory.size > 10) {
            currentHistory.removeAt(currentHistory.size - 1)
        }

        _uiState.value = _uiState.value.copy(history = currentHistory)
    }

    fun saveInstanceState(query: String) {
        // State is automatically saved by Compose, this is for explicit handling
        _uiState.value = _uiState.value.copy(query = query)
    }
}
