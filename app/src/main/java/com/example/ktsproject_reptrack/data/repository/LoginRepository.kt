package com.example.ktsproject_reptrack.data.repository

class LoginRepository {

    fun login(username: String, password: String): Result<Unit> {
        return if (username == "test@test.com" && password == "123456") {
            Result.success(Unit)
        } else {
            Result.failure(Exception("Invalid login or password"))
        }
    }
}
