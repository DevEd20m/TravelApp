package com.deved.data.repository

import com.deved.data.common.DataResponse
import com.deved.data.source.PlaceDataSource
import com.deved.domain.Department
import com.deved.domain.Places
import com.deved.domain.User

class PlacesRepositoryImpl(private val remoteDataSource: PlaceDataSource):PlacesRepository {
    override suspend fun getAllPlaces(): DataResponse<List<Places>> {
        return remoteDataSource.fetchAllPlaces()
    }
    override suspend fun getAllDepartment(): DataResponse<List<Department>> {
        return remoteDataSource.fetchAllDepartment()
    }
    override suspend fun registerExp(place:Department,userId:String):DataResponse<String>{
        return remoteDataSource.registerExp(place,userId)
    }
    override suspend fun getDetailPlace(departmentName:String,placeName : String):DataResponse<Places>{
        return remoteDataSource.getDetailPlace(departmentName,placeName)
    }
    override suspend fun getDetailUserPosted(userId:String?):DataResponse<User>{
        return remoteDataSource.getDetailUserPublishedPlace(userId)
    }
}
