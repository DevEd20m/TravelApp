package com.deved.data.repository

import com.deved.data.source.DataBaseDataSource
import com.deved.data.source.LocationDataSource

class LocationRepositoryImpl(
    private val dataBaseDatSource: DataBaseDataSource,
    private val locationDataSource: LocationDataSource
) : LocationRepository {

    override suspend fun saveLocation() {
        val result = locationDataSource.findLastLocation()
        dataBaseDatSource.saveLocation(result[0], result[1])
    }
}