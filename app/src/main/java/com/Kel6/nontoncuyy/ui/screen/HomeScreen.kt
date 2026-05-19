package com.Kel6.nontoncuyy.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.Kel6.nontoncuyy.data.model.Movie
import com.Kel6.nontoncuyy.ui.component.MovieItemComponent
import com.Kel6.nontoncuyy.ui.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onMovieClick: (Movie) -> Unit
) {
    val pagingItems = viewModel.movies.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "NontonCuyy",
                        color = Color.Red,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF000814)
                )
            )
        },
        containerColor = Color(0xFF000814)
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            item {
                Text(
                    "Trending",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 12.dp)
                ) {
                    items(pagingItems.itemCount) { index ->
                        pagingItems[index]?.let { movie ->
                            MovieItemComponent(movie, onMovieClick)
                        }
                    }
                }
            }

            item {
                Text(
                    "All Movies",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }

            // Grid simulation inside LazyColumn using chunks or just list the items
            items(pagingItems.itemCount) { index ->
                pagingItems[index]?.let { movie ->
                   // We could use a grid but paging 3 works best with lazy lists
                }
            }
            
            // Actually, for a proper Netflix style, we usually have multiple rows
            // For now, let's just show a list of movies below the trending slider
            items(pagingItems.itemCount) { index ->
                pagingItems[index]?.let { movie ->
                    ListItem(
                        headlineContent = { Text(movie.judul, color = Color.White) },
                        supportingContent = { Text(movie.kategori, color = Color.Gray) },
                        leadingContent = { MovieItemComponent(movie, onMovieClick) },
                        colors = ListItemDefaults.colors(containerColor = Color.Transparent)
                    )
                }
            }
        }
    }
}
