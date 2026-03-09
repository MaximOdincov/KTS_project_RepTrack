package com.example.ktsproject_reptrack.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ktsproject_reptrack.data.repository.ProductsRepositoryImpl
import com.example.ktsproject_reptrack.domain.usecase.CalculateNutritionUseCase
import com.example.ktsproject_reptrack.presentation.state.NutritionUiState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class NutritionViewModel(
    private val calculateNutritionUseCase: CalculateNutritionUseCase = CalculateNutritionUseCase(
        ProductsRepositoryImpl()
    )
) : ViewModel() {

    private val _uiState = MutableStateFlow(NutritionUiState())
    val uiState: StateFlow<NutritionUiState> = _uiState.asStateFlow()

    private val searchQueryFlow = MutableSharedFlow<String>()
    private val gramsFlow = MutableSharedFlow<String>()

    init {
        viewModelScope.launch {
            searchQueryFlow
                .debounce(800)
                .filter { it.isNotBlank() && it.length >= 3 }
                .flatMapLatest { query ->
                    flow {
                        _uiState.value = _uiState.value.copy(isLoading = true, error = null)
                        val result = calculateNutritionUseCase(query, 100.0)
                        emit(result)
                    }.catch { e ->
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = e.message ?: "An error occurred"
                        )
                    }
                }
                .collect { result ->
                    result.fold(
                        onSuccess = { nutritionResult ->
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                result = nutritionResult,
                                error = null
                            )
                        },
                        onFailure = { e ->
                            val errorMessage = when {
                                e.message?.contains("API temporarily unavailable", ignoreCase = true) == true -> e.message ?: "API Error"
                                e.message?.contains("not found", ignoreCase = true) == true -> "Product not found. Try another name"
                                else -> e.message ?: "An error occurred"
                            }

                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                error = errorMessage
                            )
                        }
                    )
                }
        }

        viewModelScope.launch {
            gramsFlow
                .debounce(500)
                .collect { grams ->
                    val currentResult = _uiState.value.result
                    if (currentResult != null && grams.isNotBlank()) {
                        val gramsValue = grams.toDoubleOrNull()
                        if (gramsValue != null && gramsValue > 0) {
                            recalculateForGrams(gramsValue, currentResult)
                        }
                    }
                }
        }
    }

    fun onProductNameChange(name: String) {
        _uiState.value = _uiState.value.copy(
            productName = name,
            grams = if (_uiState.value.grams.isBlank() && name.isNotBlank()) "100" else _uiState.value.grams,
            error = null,
            result = if (name != _uiState.value.productName) null else _uiState.value.result
        )

        viewModelScope.launch {
            searchQueryFlow.emit(name)
        }
    }

    fun onGramsChange(grams: String) {
        _uiState.value = _uiState.value.copy(
            grams = grams,
            error = null
        )

        viewModelScope.launch {
            gramsFlow.emit(grams)
        }
    }

    private fun recalculateForGrams(grams: Double, originalResult: com.example.ktsproject_reptrack.domain.entities.NutritionResult) {
        val ratio = grams / originalResult.grams
        _uiState.value = _uiState.value.copy(
            result = originalResult.copy(
                grams = grams,
                calories = originalResult.calories * ratio,
                proteins = originalResult.proteins * ratio,
                carbohydrates = originalResult.carbohydrates * ratio,
                fat = originalResult.fat * ratio
            )
        )
    }

    fun onRetry() {
        val name = _uiState.value.productName
        if (name.isNotBlank()) {
            viewModelScope.launch {
                searchQueryFlow.emit(name)
            }
        }
    }

    fun onClear() {
        _uiState.value = NutritionUiState()
    }
}
