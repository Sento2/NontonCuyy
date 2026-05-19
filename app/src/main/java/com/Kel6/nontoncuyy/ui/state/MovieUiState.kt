package com.kel6.nontoncuyy.ui.state

import com.kel6.nontoncuyy.data.model.Movie

sealed interface MovieUiState {
    object Loading : MovieUiState
    data class Success(val movies: List<Movie>) : MovieUiState
    data class Error(val message: String) : MovieUiState

    object AddLoading : MovieUiState
    object AddSuccess : MovieUiState
    data class AddError(val message: String) : MovieUiState
}
