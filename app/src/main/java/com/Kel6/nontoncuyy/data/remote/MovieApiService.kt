package com.Kel6.nontoncuyy.data.remote

import com.Kel6.nontoncuyy.data.model.Movie
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class MovieApiService(private val client: HttpClient) {
    suspend fun getMovies(page: Int, limit: Int = 10): List<Movie> {
        return client.get("film") {
            parameter("page", page)
            parameter("limit", limit)
        }.body()
    }

    suspend fun addMovie(movie: Movie): Movie {
        return client.post("film") {
            contentType(ContentType.Application.Json)
            setBody(movie)
        }.body()
    }
}
