package com.example.ktsproject_reptrack.presentation.state

import com.example.ktsproject_reptrack.domain.entities.NutritionResult

data class NutritionUiState(
    val productName: String = "",
    val grams: String = "",
    val isLoading: Boolean = false,
    val result: NutritionResult? = null,
    val error: String? = null,
    val isInputValid: Boolean = false
)
