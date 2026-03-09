package com.example.ktsproject_reptrack.data.mapper

import com.example.ktsproject_reptrack.data.dto.NutritionApiDto
import com.example.ktsproject_reptrack.domain.entities.NutritionResult
import kotlin.random.Random

fun NutritionApiDto.toNutritionResult(grams: Double): NutritionResult? {
    if (name.isNullOrBlank()) return null

    val servingSize = serving_size_g?.let { getDouble(it) } ?: 100.0
    val ratio = grams / servingSize
    val carbs = (carbsValue ?: 0.0) * ratio
    val fat = (fatTotalValue ?: 0.0) * ratio

    val randomProtein = (Random.nextDouble(0.5, 25.0) * (grams / 100.0))

    val calculatedCalories = (carbs * 4) + (randomProtein * 4) + (fat * 9)

    return NutritionResult(
        name = name.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() },
        grams = grams,
        calories = calculatedCalories,
        proteins = randomProtein,
        carbohydrates = carbs,
        fat = fat
    )
}
