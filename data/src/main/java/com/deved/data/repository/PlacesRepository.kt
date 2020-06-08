package com.deved.data.repository

import com.deved.data.common.DataResponse
import com.deved.data.source.PlaceDataSource
import com.deved.domain.Department
import com.deved.domain.Places

class PlacesRepository(private val remoteDataSource: PlaceDataSource) {

    suspend fun getAllPlaces(): DataResponse<List<Places>> {
        return remoteDataSource.fetchAllPlaces()
    }
    suspend fun registerExp(place:Department):DataResponse<String>{
        return remoteDataSource.registerExp(place)
    }
    suspend fun getDetailPlace(placeName : String):DataResponse<Places>{
        return remoteDataSource.getDetailPlace(placeName)
    }
}
