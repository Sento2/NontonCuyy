package com.Kel6.nontoncuyy.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.Kel6.nontoncuyy.data.model.Movie

@Composable
fun MovieItemComponent(
    movie: Movie,
    onMovieClick: (Movie) -> Unit
) {
    Card(
        modifier = Modifier
            .width(130.dp)
            .height(180.dp)
            .padding(4.dp)
            .clickable { onMovieClick(movie) },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors()
    ) {
        AsyncImage(
            model = movie.gambar_poster,
            contentDescription = movie.judul,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
    }
}
