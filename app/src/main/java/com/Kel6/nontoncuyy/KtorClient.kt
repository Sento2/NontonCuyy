package com.Kel6.nontoncuyy

import android.util.Log
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object KtorClient {
    // URL utama dari MockAPI dosenmu
    private const val BASE_URL = "https://68ff8dfbe02b16d1753e765d.mockapi.io/film"

    val httpClient = HttpClient(Android) {
        // Pengaturan batas waktu (mencegah aplikasi freeze jika internet lemot)
        install(HttpTimeout) {
            requestTimeoutMillis = 15000L
            connectTimeoutMillis = 15000L
            socketTimeoutMillis = 15000L
        }

        // Pengaturan Logging untuk memantau request & response di Logcat Android Studio
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.d("KtorLog", message)
                }
            }
            level = LogLevel.ALL
        }

        // Pengaturan JSON parsing otomatis
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true // Mengabaikan field tak dikenal agar aplikasi tidak crash
                isLenient = true
            })
        }

        // Set Base URL otomatis untuk setiap request
        defaultRequest {
            url(BASE_URL)
        }
    }
}