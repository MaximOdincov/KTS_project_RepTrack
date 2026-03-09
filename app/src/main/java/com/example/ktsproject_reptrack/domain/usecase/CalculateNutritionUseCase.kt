package com.example.ktsproject_reptrack.domain.usecase

import com.example.ktsproject_reptrack.domain.entities.NutritionResult
import com.example.ktsproject_reptrack.domain.repository.ProductsRepository

class CalculateNutritionUseCase(
    private val productsRepository: ProductsRepository
) {
    suspend operator fun invoke(query: String, grams: Double): Result<NutritionResult> {
        return productsRepository.calculateNutrition(query, grams)
    }
}
