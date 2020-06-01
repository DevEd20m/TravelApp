package com.deved.data.repository

import com.deved.data.common.DataResponse
import com.deved.data.source.RemoteDataSource
import com.deved.domain.Places

class PlacesRepository(private val remoteDataSource: RemoteDataSource) {

    suspend fun getAllPlaces(): DataResponse<List<Places>> {
        return remoteDataSource.fetchAllPlaces()
    }
    suspend fun registerExp(place:Places):DataResponse<String>{
        return remoteDataSource.registerExp(place)
    }

}
