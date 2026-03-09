package com.example.ktsproject_reptrack.domain.entities

data class NutritionResult(
    val name: String,
    val grams: Double,
    val calories: Double,
    val proteins: Double,
    val carbohydrates: Double,
    val fat: Double
)
