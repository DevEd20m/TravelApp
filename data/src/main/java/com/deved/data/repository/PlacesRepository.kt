package com.deved.data.repository

import com.deved.data.common.DataResponse
import com.deved.domain.Department
import com.deved.domain.Places
import com.deved.domain.User

interface PlacesRepository {
    suspend fun getAllPlaces(): DataResponse<List<Places>>
    suspend fun getAllDepartment(): DataResponse<List<Department>>
    suspend fun registerExp(place: Department, userId: String): DataResponse<String>
    suspend fun getDetailPlace(departmentName: String, placeName: String): DataResponse<Places>
    suspend fun getDetailUserPosted(userId: String?): DataResponse<User>
}