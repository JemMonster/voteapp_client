package com.example.voteapp.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object HttpClientFactory {

    fun create(baseUrl: String): HttpClient {
        return HttpClient(Android) {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                        prettyPrint = false
                    },
                )
            }

            // Базовый URL + дефолтные настройки запросов
            defaultRequest {
                url(baseUrl)
                // Content-Type на всякий случай
                contentType(ContentType.Application.Json)
            }

            install(io.ktor.client.plugins.logging.Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.INFO
            }
        }
    }
}

