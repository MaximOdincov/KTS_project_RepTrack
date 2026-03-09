package com.example.ktsproject_reptrack.data.datasource

import com.example.ktsproject_reptrack.BuildConfig
import com.example.ktsproject_reptrack.data.dto.NutritionApiDto
import com.example.ktsproject_reptrack.data.mapper.toNutritionResult
import com.example.ktsproject_reptrack.data.network.KtorConfig
import com.example.ktsproject_reptrack.domain.entities.NutritionResult
import io.github.aakira.napier.Napier
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

object KtorProductsDataSource {
    private const val BASE_URL = "https://api.api-ninjas.com/v1/nutrition"
    private const val MAX_RETRIES = 3
    private const val RETRY_DELAY_MS = 1000L

    suspend fun calculateNutrition(query: String, grams: Double): Result<NutritionResult> = withContext(Dispatchers.IO) {
        var lastException: Exception? = null

        for (attempt in 0 until MAX_RETRIES) {
            try {
                if (attempt > 0) {
                    delay(RETRY_DELAY_MS)
                }

                val trimmedQuery = query.trim()
                Napier.i("Requesting nutrition for: '$trimmedQuery' ($grams grams)")

                val response = KtorConfig.httpClient.get(BASE_URL) {
                    header("X-Api-Key", BuildConfig.API_NINJAS_KEY)
                    parameter("query", trimmedQuery)
                }

                Napier.i("Response status: ${response.status.value} ${response.status.description}")

                if (response.status.value in 500..599) {
                    val errorBody = response.body<String>()
                    Napier.w("Server error (${response.status.value}): $errorBody")
                    lastException = Exception("API Error (${response.status.value}): $errorBody")

                    if (attempt < MAX_RETRIES - 1) {
                        continue
                    } else {
                        return@withContext Result.failure(
                            Exception("API temporarily unavailable. Please try again in a few minutes.")
                        )
                    }
                }

                if (response.status != HttpStatusCode.OK) {
                    val errorBody = response.body<String>()
                    Napier.e("API Error ($response.status): $errorBody")
                    return@withContext Result.failure(
                        Exception("API Error (${response.status.value}): $errorBody")
                    )
                }

                val apiResponse: List<NutritionApiDto> = response.body()

                if (apiResponse.isEmpty()) {
                    Napier.w("No nutrition data found for: $query")
                    return@withContext Result.failure(
                        NoSuchElementException("Product '$query' not found. Try a different name")
                    )
                }

                val result = apiResponse.firstOrNull()?.toNutritionResult(grams)
                if (result == null) {
                    Napier.e("Failed to parse nutrition data from API response")
                    return@withContext Result.failure(
                        Exception("Failed to parse nutrition data")
                    )
                }
                Napier.i("Successfully calculated nutrition for: ${result.name}")
                return@withContext Result.success(result)

            } catch (e: Exception) {
                Napier.e("Attempt ${attempt + 1} failed for: $query", e)
                lastException = e

                if (attempt < MAX_RETRIES - 1) {
                    continue
                }
            }
        }

        Napier.e("All $MAX_RETRIES attempts failed")
        Result.failure(
            lastException ?: Exception("Failed after $MAX_RETRIES attempts")
        )
    }
}
