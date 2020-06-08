package com.deved.data.repository

import com.deved.data.common.DataResponse
import com.deved.data.source.PlaceDataSource
import com.deved.domain.Department
import com.deved.domain.Places

class PlacesRepository(private val remoteDataSource: PlaceDataSource) {

    suspend fun getAllPlaces(): DataResponse<List<Places>> {
        return remoteDataSource.fetchAllPlaces()
    }
    suspend fun getAllDepartment(): DataResponse<List<Department>> {
        return remoteDataSource.fetchAllDepartment()
    }
    suspend fun registerExp(place:Department):DataResponse<String>{
        return remoteDataSource.registerExp(place)
    }
    suspend fun getDetailPlace(departmentName:String,placeName : String):DataResponse<Places>{
        return remoteDataSource.getDetailPlace(departmentName,placeName)
    }
}
