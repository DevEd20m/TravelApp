package com.deved.data.repository

import com.deved.data.common.DataResponse
import com.deved.data.source.SecurityDataSource
import com.deved.domain.User

class SecurityRepositoryImpl(private val remoteDataSource: SecurityDataSource):SecurityRepository{

    override suspend fun logIn(user: String, password: String): DataResponse<User> {
        return when(val value = remoteDataSource.logIn(user, password)){
            is DataResponse.Success -> remoteDataSource.getProfile(value.data)
            is DataResponse.NetworkError -> DataResponse.NetworkError(value.error)
            is DataResponse.TimeOutServerError -> DataResponse.TimeOutServerError(value.error)
            is DataResponse.ExceptionError -> DataResponse.ExceptionError(value.errorCode)
            is DataResponse.ServerError -> DataResponse.ServerError(value.errorCode)
        }
    }

    override suspend fun getUserProfile(userId: String): DataResponse<User> {
        return remoteDataSource.getProfile(userId)
    }

    override suspend fun registerUser(user: User): DataResponse<String> {
        return remoteDataSource.registerUser(user)
    }
}