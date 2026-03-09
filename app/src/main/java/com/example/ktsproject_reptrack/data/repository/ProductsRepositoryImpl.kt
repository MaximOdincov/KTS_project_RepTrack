package com.example.ktsproject_reptrack.data.repository

import com.example.ktsproject_reptrack.data.datasource.KtorProductsDataSource
import com.example.ktsproject_reptrack.domain.entities.NutritionResult
import com.example.ktsproject_reptrack.domain.repository.ProductsRepository

class ProductsRepositoryImpl : ProductsRepository {
    override suspend fun calculateNutrition(query: String, grams: Double): Result<NutritionResult> {
        return KtorProductsDataSource.calculateNutrition(query, grams)
    }
}
