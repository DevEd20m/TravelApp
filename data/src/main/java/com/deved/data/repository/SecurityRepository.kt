package com.deved.data.repository

import com.deved.data.common.DataResponse
import com.deved.data.source.SecurityDataSource
import com.deved.domain.User

class SecurityRepository(private val remoteDataSource: SecurityDataSource) {

    suspend fun logIn(user: String, password: String): DataResponse<User> {
        return when(val value = remoteDataSource.logIn(user, password)){
            is DataResponse.Success -> remoteDataSource.getProfile(value.data)
            is DataResponse.NetworkError -> DataResponse.NetworkError(value.error)
            is DataResponse.TimeOutServerError -> DataResponse.TimeOutServerError(value.error)
            is DataResponse.ExceptionError -> DataResponse.ExceptionError(value.errorCode)
            is DataResponse.ServerError -> DataResponse.ServerError(value.errorCode)
        }
    }

    suspend fun getUserProfile(userId: String): DataResponse<User> {
        return remoteDataSource.getProfile(userId)
    }

    suspend fun registerUser(user: User): DataResponse<String> {
        return remoteDataSource.registerUser(user)
    }
}