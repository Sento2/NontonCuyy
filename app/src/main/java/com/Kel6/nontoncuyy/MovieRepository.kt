package com.Kel6.nontoncuyy

import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository {
    private val client = KtorClient.httpClient

    // Fungsi utama untuk mengambil semua data film dari endpoint /film
    suspend fun getAllFilms(): List<Film> {
        return withContext(Dispatchers.IO) { // Diproses di background thread agar UI tetap lancar
            try {
                // Ktor akan langsung membaca JSON Array dari dosen menjadi List<Film>
                val response: List<Film> = client.get("film").body()
                response
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList() // Jika server dosen down atau internet putus, kembalikan list kosong
            }
        }
    }
}