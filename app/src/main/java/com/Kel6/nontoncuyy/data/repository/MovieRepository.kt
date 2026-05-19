package com.kel6.nontoncuyy.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kel6.nontoncuyy.data.model.Movie
import com.kel6.nontoncuyy.data.remote.MovieApiService
import com.kel6.nontoncuyy.data.remote.MoviePagingSource
import kotlinx.coroutines.flow.Flow

class MovieRepository(private val apiService: MovieApiService) {
    fun getMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviePagingSource(apiService) }
        ).flow
    }

    suspend fun addMovie(movie: Movie): Movie {
        return apiService.addMovie(movie)
    }

}
