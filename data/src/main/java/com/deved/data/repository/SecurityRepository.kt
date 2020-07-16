package com.deved.data.repository

import com.deved.data.common.DataResponse
import com.deved.domain.User

interface SecurityRepository {
    suspend fun logIn(user: String, password: String): DataResponse<User>
    suspend fun getUserProfile(userId: String): DataResponse<User>
    suspend fun registerUser(user: User): DataResponse<String>
}