package com.deved.data.repository

import com.deved.data.common.DataResponse
import com.deved.data.source.PlaceDataSource
import com.deved.domain.Department
import com.deved.domain.Places
import com.deved.domain.User

class PlacesRepository(private val remoteDataSource: PlaceDataSource) {

    suspend fun getAllPlaces(): DataResponse<List<Places>> {
        return remoteDataSource.fetchAllPlaces()
    }
    suspend fun getAllDepartment(): DataResponse<List<Department>> {
        return remoteDataSource.fetchAllDepartment()
    }
    suspend fun registerExp(place:Department,userId:String):DataResponse<String>{
        return remoteDataSource.registerExp(place,userId)
    }
    suspend fun getDetailPlace(departmentName:String,placeName : String):DataResponse<Places>{
        return remoteDataSource.getDetailPlace(departmentName,placeName)
    }
    suspend fun getDetailUserPosted(userId:String?):DataResponse<User>{
        return remoteDataSource.getDetailUserPublishedPlace(userId)
    }
}
