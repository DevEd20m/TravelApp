package com.deved.data.repository

import com.deved.data.repository.PermissionsChecker.Permissions.COARSE_LOCATION
import com.deved.data.source.DataBaseDataSource
import com.deved.data.source.LocationDataSource

class LocationRepository(
    private val dataBaseDatSource: DataBaseDataSource,
    private val locationDataSource: LocationDataSource,
    private val permissionsChecker: PermissionsChecker? = null
) {

    fun validatePermission():Boolean? {
        return permissionsChecker?.checkPermission(COARSE_LOCATION)
    }

    fun requestPermission() {
        permissionsChecker?.let {
            permissionsChecker.requestPermissionsLocation()
        }
    }

    suspend fun saveLocation() {
        val result = locationDataSource.findLastLocation()
        dataBaseDatSource.saveLocation(result[0], result[1])
    }
}

