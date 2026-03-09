package com.example.ktsproject_reptrack.domain.repository

import com.example.ktsproject_reptrack.domain.entities.NutritionResult

interface ProductsRepository {
    suspend fun calculateNutrition(query: String, grams: Double): Result<NutritionResult>
}
