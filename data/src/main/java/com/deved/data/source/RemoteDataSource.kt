package com.deved.data.source

import com.deved.data.common.DataResponse
import com.deved.domain.Places
import com.deved.domain.User

interface RemoteDataSource {
    suspend fun logIn(user:String, password:String):DataResponse<User>
    suspend fun registerUser():DataResponse<User>
    suspend fun fetchAllPlaces(): DataResponse<List<Places>>
}