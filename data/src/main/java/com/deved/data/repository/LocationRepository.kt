package com.deved.data.repository

import com.deved.data.source.DataBaseDataSource

class LocationRepository(private val localDataSource: DataBaseDataSource) {

    suspend fun saveLocation(longitude:Double,latitude:Double){
        localDataSource.saveLocation(longitude, latitude)
    }
}