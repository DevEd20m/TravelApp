package com.deved.data.repository

import com.deved.data.common.DataResponse
import com.deved.data.source.SecurityDataSource
import com.deved.domain.User

class SecurityRepository(private val remoteDataSource: SecurityDataSource) {

    suspend fun logIn(user: String, password: String): DataResponse<String> {
        return remoteDataSource.logIn(user, password)
    }

    suspend fun registerUser(user: User):DataResponse<String>{
        return remoteDataSource.registerUser(user)
    }
}