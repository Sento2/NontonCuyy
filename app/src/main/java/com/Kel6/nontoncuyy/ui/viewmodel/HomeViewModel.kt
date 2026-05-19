package com.kel6.nontoncuyy.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kel6.nontoncuyy.data.model.Movie
import com.kel6.nontoncuyy.data.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class HomeViewModel(private val repository: MovieRepository) : ViewModel() {
    val movies: Flow<PagingData<Movie>> = repository.getMoviesPaged()
        .cachedIn(viewModelScope)
}
