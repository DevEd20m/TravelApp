package com.deved.data.source

import com.deved.data.common.DataResponse
import com.deved.domain.Department
import com.deved.domain.Places
import com.deved.domain.User

interface PlaceDataSource {
    suspend fun fetchAllPlaces(): DataResponse<List<Places>>
    suspend fun fetchAllDepartment(): DataResponse<List<Department>>
    suspend fun getDetailPlace(departmentName: String, placeName: String): DataResponse<Places>
    suspend fun registerExp(data: Department, userId: String): DataResponse<String>
}