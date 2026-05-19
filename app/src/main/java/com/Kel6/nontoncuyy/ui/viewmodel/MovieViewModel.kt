package com.Kel6.nontoncuyy.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Kel6.nontoncuyy.data.model.Movie
import com.Kel6.nontoncuyy.data.repository.MovieRepository
import com.Kel6.nontoncuyy.ui.state.MovieUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<MovieUiState>(MovieUiState.Loading)
    val uiState: StateFlow<MovieUiState> = _uiState.asStateFlow()

    init {
        getAllMovies()
    }

    fun getAllMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = MovieUiState.Loading
            try {
                val movies = repository.getAllMovies()
                _uiState.value = MovieUiState.Success(movies)
            } catch (e: Exception) {
                _uiState.value = MovieUiState.Error(e.message ?: "Terjadi kesalahan saat mengambil data")
            }
        }
    }

    fun tambahDataFilm(filmBaru: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = MovieUiState.AddLoading
            try {
                repository.addMovie(filmBaru)
                _uiState.value = MovieUiState.AddSuccess
            } catch (e: Exception) {
                _uiState.value = MovieUiState.AddError(e.message ?: "Gagal menambahkan film baru")
            }
        }
    }

    fun resetStatus() {
        getAllMovies()
    }
}
