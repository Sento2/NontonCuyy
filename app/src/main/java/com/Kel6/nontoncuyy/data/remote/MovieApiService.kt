package com.kel6.nontoncuyy.data.remote

import com.kel6.nontoncuyy.data.model.Movie
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class MovieApiService(private val client: HttpClient) {
    suspend fun getMovies(page: Int, limit: Int = 10): List<Movie> {
        return client.get("film") {
            parameter("page", page)
            parameter("limit", limit)
        }.body()
    }
}
