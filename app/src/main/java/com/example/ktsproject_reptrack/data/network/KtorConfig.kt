package com.example.ktsproject_reptrack.data.network

import android.util.Log
import io.github.aakira.napier.Antilog
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import io.github.aakira.napier.LogLevel as NapierLogLevel
import io.ktor.client.plugins.logging.LogLevel as KtorLogLevel

object KtorConfig {
    val httpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
        install(Logging) {
            logger = Logger.SIMPLE
            level = KtorLogLevel.ALL
        }
    }

    init {
        Napier.base(object : Antilog() {
            override fun performLog(
                priority: NapierLogLevel,
                tag: String?,
                throwable: Throwable?,
                message: String?
            ) {
                val messageText = message ?: "Empty message"
                val finalTag = tag ?: "Napier"

                when (priority) {
                    NapierLogLevel.VERBOSE -> Log.v(finalTag, messageText, throwable)
                    NapierLogLevel.DEBUG -> Log.d(finalTag, messageText, throwable)
                    NapierLogLevel.INFO -> Log.i(finalTag, messageText, throwable)
                    NapierLogLevel.WARNING -> Log.w(finalTag, messageText, throwable)
                    NapierLogLevel.ERROR -> Log.e(finalTag, messageText, throwable)
                    NapierLogLevel.ASSERT -> Log.wtf(finalTag, messageText, throwable)
                }
            }
        })
    }
}
