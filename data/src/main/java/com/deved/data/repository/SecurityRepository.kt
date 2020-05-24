package com.deved.data.repository

import com.deved.data.common.DataResponse
import com.deved.data.source.RemoteDataSource
import com.deved.domain.User

class SecurityRepository(private val remoteDataSource: RemoteDataSource) {

    suspend fun logIn(user: String, password: String): DataResponse<User> {
        return remoteDataSource.logIn(user, password)
    }
}