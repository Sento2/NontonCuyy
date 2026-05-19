package com.Kel6.nontoncuyy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {
    private val repository = MovieRepository()

    private val _movies = MutableLiveData<List<Film>>()
    val movies: LiveData<List<Film>> = _movies

    private val _filteredMovies = MutableLiveData<List<Film>>()
    val filteredMovies: LiveData<List<Film>> = _filteredMovies

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _watchlist = MutableLiveData<List<Film>>(emptyList())
    val watchlist: LiveData<List<Film>> = _watchlist

    fun toggleWatchlist(film: Film) {
        val currentList = _watchlist.value?.toMutableList() ?: mutableListOf()
        if (currentList.any { it.id == film.id }) {
            currentList.removeAll { it.id == film.id }
        } else {
            currentList.add(film)
        }
        _watchlist.value = currentList
    }

    fun isInWatchlist(filmId: String): Boolean {
        return _watchlist.value?.any { it.id == filmId } ?: false
    }

    fun fetchMovies() {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.getAllFilms()
            _movies.value = result
            _filteredMovies.value = result
            _isLoading.value = false
        }
    }

    fun searchMovies(query: String) {
        val allMovies = _movies.value ?: return
        if (query.isEmpty()) {
            _filteredMovies.value = allMovies
        } else {
            val filtered = allMovies.filter { 
                it.judul.contains(query, ignoreCase = true) || 
                it.ringkasan.contains(query, ignoreCase = true) ||
                it.kategori.equals(query, ignoreCase = true) ||
                it.kategori.contains(query, ignoreCase = true)
            }
            _filteredMovies.value = filtered
        }
    }

    fun filterByGenre(genre: String) {
        val allMovies = _movies.value ?: return
        if (genre.isEmpty() || genre.equals("All", ignoreCase = true)) {
            _filteredMovies.value = allMovies
        } else {
            val filtered = allMovies.filter { 
                it.kategori.contains(genre, ignoreCase = true)
            }
            _filteredMovies.value = filtered
        }
    }
}