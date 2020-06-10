package com.deved.interactors

import com.deved.data.repository.LocationRepository

class SaveLocation(private val repository: LocationRepository) {
    suspend fun invoke(longitude: Double, latitude: Double) =
        repository.saveLocation(longitude, latitude)
}