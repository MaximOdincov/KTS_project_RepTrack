package com.example.ktsproject_reptrack.domain.entities

data class Exercise(
    val id: String,
    val name: String,
    val muscleGroup: MuscleGroup,
    val type: ExerciseType,
    val backgroundColor: String?,
    val iconRes: Int?,
    val iconColor: String?,
)
