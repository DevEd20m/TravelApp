package com.deved.data.source

import com.deved.data.common.DataResponse
import com.deved.domain.Department
import com.deved.domain.Places
import com.deved.domain.User

interface RemoteDataSource {
    suspend fun fetchAllPlaces(): DataResponse<List<Department>>
    suspend fun registerExp(place:Department):DataResponse<String>
}