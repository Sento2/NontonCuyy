package com.kel6.nontoncuyy.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kel6.nontoncuyy.data.model.Movie
import com.kel6.nontoncuyy.ui.state.MovieUiState
import com.kel6.nontoncuyy.ui.viewmodel.MovieViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieScreen(
    viewModel: MovieViewModel,
    onMovieClick: (Movie) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Handle side effects
    LaunchedEffect(uiState) {
        if (uiState is MovieUiState.AddSuccess) {
            viewModel.resetStatus()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("NontonCuyy - Daftar Film") })
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (val state = uiState) {
                is MovieUiState.Loading, is MovieUiState.AddLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is MovieUiState.Success -> {
                    MovieContent(movies = state.movies, onMovieClick = onMovieClick)
                }
                is MovieUiState.Error -> {
                    ErrorContent(message = state.message, onRetry = { viewModel.getAllMovies() })
                }
                is MovieUiState.AddSuccess -> {
                    // Handled by LaunchedEffect, show loading while transitioning
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is MovieUiState.AddError -> {
                    ErrorContent(message = state.message, onRetry = { viewModel.resetStatus() })
                }
            }
        }
    }
}

@Composable
fun MovieContent(
    movies: List<Movie>,
    onMovieClick: (Movie) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(movies) { movie ->
            Card(
                onClick = { onMovieClick(movie) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = movie.judul, style = MaterialTheme.typography.titleLarge)
                    Text(text = movie.kategori, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

@Composable
fun ErrorContent(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Eror: $message", color = MaterialTheme.colorScheme.error)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onRetry) {
            Text("Coba Lagi")
        }
    }
}
