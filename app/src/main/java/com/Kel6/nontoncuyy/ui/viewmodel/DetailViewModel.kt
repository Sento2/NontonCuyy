package com.Kel6.nontoncuyy.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.Kel6.nontoncuyy.data.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DetailViewModel : ViewModel() {
    private val _selectedMovie = MutableStateFlow<Movie?>(null)
    val selectedMovie = _selectedMovie.asStateFlow()

    fun selectMovie(movie: Movie) {
        _selectedMovie.value = movie
    }
}
