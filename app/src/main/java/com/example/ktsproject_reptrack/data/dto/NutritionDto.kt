package com.example.ktsproject_reptrack.data.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive

@Serializable
data class NutritionApiDto(
    val name: String? = null,
    val calories: JsonElement? = null,
    val serving_size_g: JsonElement? = null,
    val fat_total_g: JsonElement? = null,
    val fat_saturated_g: JsonElement? = null,
    val protein_g: JsonElement? = null,
    val sodium_mg: JsonElement? = null,
    val potassium_mg: JsonElement? = null,
    val cholesterol_mg: JsonElement? = null,
    val carbohydrates_total_g: JsonElement? = null,
    val fiber_g: JsonElement? = null,
    val sugar_g: JsonElement? = null
) {
    fun getDouble(field: JsonElement?): Double? {
        if (field == null) return null
        return when (field) {
            is JsonPrimitive -> {
                val content = field.content
                if (content.contains("premium") || content.contains("subscriber")) {
                    null
                } else {
                    content.toDoubleOrNull()
                }
            }
            else -> null
        }
    }

    val caloriesValue: Double? get() = getDouble(calories)
    val fatTotalValue: Double? get() = getDouble(fat_total_g)
    val proteinValue: Double? get() = getDouble(protein_g)
    val carbsValue: Double? get() = getDouble(carbohydrates_total_g)
}

