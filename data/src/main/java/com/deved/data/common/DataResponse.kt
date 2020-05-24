package com.deved.data.common

sealed class DataResponse<out T : Any> {
    data class Success<T : Any>(val data: T) : DataResponse<T>()
    data class NetworkError(val error: String) : DataResponse<Nothing>()
    data class TimeOutServerError(val error: String) : DataResponse<Nothing>()
    data class ExceptionError(val errorCode: Exception) : DataResponse<Nothing>()
}