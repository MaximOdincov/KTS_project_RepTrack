package com.example.ktsproject_reptrack.data.repository

import com.example.ktsproject_reptrack.R
import com.example.ktsproject_reptrack.domain.entities.Exercise
import com.example.ktsproject_reptrack.domain.entities.ExerciseType
import com.example.ktsproject_reptrack.domain.entities.MuscleGroup
import kotlinx.coroutines.delay

class ExercisesRepository {

    suspend fun getExercises(): Result<List<Exercise>> {
        delay(500)

        return Result.success(
            listOf(
                Exercise(
                    id = "1",
                    name = "Bench press",
                    muscleGroup = MuscleGroup.CHEST,
                    type = ExerciseType.WEIGHT_REPS,
                    iconRes = R.drawable.exercise_bench_press,
                    iconColor = "#FF8C42",
                    backgroundColor = null,
                ),
                Exercise(
                    id = "2",
                    name = "Barbell Squats",
                    muscleGroup = MuscleGroup.LEGS,
                    type = ExerciseType.WEIGHT_REPS,
                    iconRes = R.drawable.exercis_icon_2,
                    iconColor = "#4CAF50",
                    backgroundColor = null
                ),
                Exercise(
                    id = "3",
                    name = "Deadlift",
                    muscleGroup = MuscleGroup.BACK,
                    type = ExerciseType.WEIGHT_REPS,
                    iconRes = R.drawable.exercise_icon_3,
                    iconColor = "#FF5252",
                    backgroundColor = null,
                ),
                Exercise(
                    id = "4",
                    name = "Pull-ups",
                    muscleGroup = MuscleGroup.BACK,
                    type = ExerciseType.WEIGHT_REPS,
                    iconRes = R.drawable.exercise_icon_4,
                    iconColor = "#2196F3",
                    backgroundColor = null,
                ),
                Exercise(
                    id = "5",
                    name = "Army Bench Press",
                    muscleGroup = MuscleGroup.ARMS,
                    type = ExerciseType.WEIGHT_REPS,
                    iconRes = R.drawable.exercise_bench_press,
                    iconColor = "#FF9800",
                    backgroundColor = null,
                ),
                Exercise(
                    id = "6",
                    name = "Running on the track",
                    muscleGroup = MuscleGroup.CARDIO,
                    type = ExerciseType.TIME_DISTANCE,
                    iconRes = R.drawable.exercis_icon_2,
                    iconColor = "#9C27B0",
                    backgroundColor = null
                ),
                Exercise(
                    id = "7",
                    name = "Twisting on the press",
                    muscleGroup = MuscleGroup.ABS,
                    type = ExerciseType.WEIGHT_REPS,
                    iconRes = R.drawable.exercise_icon_3,
                    iconColor = "#F44336",
                    backgroundColor = null
                ),
                Exercise(
                    id = "8",
                    name = "Dumbbell lunges",
                    muscleGroup = MuscleGroup.LEGS,
                    type = ExerciseType.WEIGHT_REPS,
                    iconRes = R.drawable.exercise_icon_4,
                    iconColor = "#00BCD4",
                    backgroundColor = null
                )
            )
        )
    }
}
