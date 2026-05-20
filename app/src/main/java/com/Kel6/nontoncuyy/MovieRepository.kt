package com.Kel6.nontoncuyy

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository {
    private val client = KtorClient.httpClient

    // Fungsi utama untuk mengambil semua data film dari endpoint /film
    suspend fun getAllFilms(): List<Film> {
        return withContext(Dispatchers.IO) { 
            try {
                val response: List<Film> = client.get("film").body()
                response
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList<Film>()
            }
        }
    }

    // Fungsi untuk menambahkan film baru (POST)
    suspend fun addFilm(film: Film): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = client.post("film") {
                    contentType(ContentType.Application.Json)
                    setBody(film)
                }
                response.status.value in 200..299
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }
}
