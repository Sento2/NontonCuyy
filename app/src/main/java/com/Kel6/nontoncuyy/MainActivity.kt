package com.Kel6.nontoncuyy

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.Kel6.nontoncuyy.ui.screen.DetailScreen
import com.Kel6.nontoncuyy.ui.screen.HomeScreen
import com.Kel6.nontoncuyy.ui.screen.MoviePlayerScreen
import com.Kel6.nontoncuyy.ui.viewmodel.DetailViewModel
import com.Kel6.nontoncuyy.ui.viewmodel.HomeViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NontonCuyyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val detailViewModel: DetailViewModel = koinViewModel()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            val homeViewModel: HomeViewModel = koinViewModel()
            HomeScreen(
                viewModel = homeViewModel,
                onMovieClick = { movie ->
                    detailViewModel.selectMovie(movie)
                    navController.navigate("detail")
                }
            )
        }
        composable("detail") {
            val movie by detailViewModel.selectedMovie.collectAsState()
            DetailScreen(
                movie = movie,
                onWatchTrailer = { url ->
                    if (url.isNotEmpty()) {
                        val encodedUrl = Uri.encode(url)
                        navController.navigate("player?url=$encodedUrl")
                    }
                }
            )
        }
        composable(
            route = "player?url={url}",
            arguments = listOf(navArgument("url") { 
                type = NavType.StringType
                nullable = true
                defaultValue = ""
            })
        ) { backStackEntry ->
            val url = backStackEntry.arguments?.getString("url") ?: ""
            MoviePlayerScreen(urlTrailer = url)
        }
    }
}

@Composable
fun NontonCuyyTheme(content: @Composable () -> Unit) {
    val darkColors = darkColorScheme(
        primary = Color.Red,
        background = Color(0xFF000814),
        surface = Color(0xFF000814),
        onBackground = Color.White,
        onSurface = Color.White
    )
    MaterialTheme(
        colorScheme = darkColors,
        content = content
    )
}
