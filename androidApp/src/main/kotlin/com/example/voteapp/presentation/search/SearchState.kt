package com.example.voteapp.presentation.search

sealed class SearchState {
    object Initial : SearchState()
    object Loading : SearchState()
    data class Success(val results: List<VotingSearchResult>) : SearchState()
    data class Error(val message: String, val lastQuery: String) : SearchState()
    object NoResults : SearchState()
}

data class VotingSearchResult(
    val id: String,
    val title: String,
    val description: String?,
    val type: String,
    val status: String
)
