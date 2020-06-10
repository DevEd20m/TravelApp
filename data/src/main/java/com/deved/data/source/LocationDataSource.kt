package com.deved.data.source

interface LocationDataSource {
    suspend fun findLastLocation():List<Double>
}