package com.deved.myepxinperu.data.device

import android.app.Application
import com.deved.data.source.LocationDataSource
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.tasks.await

class PlayServicesLocationDataSource(application: Application) : LocationDataSource {
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)
    override suspend fun findLastLocation(): List<Double> {
        val list = mutableListOf<Double>()
        list.add(fusedLocationClient.lastLocation.await().latitude)
        list.add(fusedLocationClient.lastLocation.await().longitude)
        return list
    }
}