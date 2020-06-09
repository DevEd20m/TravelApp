package com.deved.data.source

import com.deved.data.common.DataResponse
import com.deved.domain.User

interface SecurityDataSource {
    suspend fun logIn(user:String, password:String): DataResponse<String>
    suspend fun getProfile(userId:String?): DataResponse<User>
    suspend fun registerUser(user: User): DataResponse<String>
}