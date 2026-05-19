package com.Kel6.nontoncuyy.di

import com.Kel6.nontoncuyy.data.remote.MovieApiService
import com.Kel6.nontoncuyy.data.repository.MovieRepository
import com.Kel6.nontoncuyy.ui.viewmodel.HomeViewModel
import com.Kel6.nontoncuyy.ui.viewmodel.DetailViewModel
import com.Kel6.nontoncuyy.ui.viewmodel.MovieViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val networkModule = module {
    single {
        HttpClient(Android) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
            install(Logging) {
                level = LogLevel.BODY
            }
            defaultRequest {
                url("https://68ff8dfbe02b16d1753e765d.mockapi.io/")
            }
        }
    }
    single { MovieApiService(get()) }
}

val repositoryModule = module {
    single { MovieRepository(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailViewModel() }
    viewModel { MovieViewModel(get()) }
}

val appModule = listOf(networkModule, repositoryModule, viewModelModule)
