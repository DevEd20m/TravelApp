package com.deved.data.source

interface DataBaseDataSource {
    suspend fun saveLocation(longitude: Double, latitude: Double)
}