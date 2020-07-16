package com.deved.myepxinperu.data.device

import android.content.Context
import com.deved.data.source.LocationDataSource

class PlayServicesLocationDataSource(application: Context) : LocationDataSource {
    //    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)
    override suspend fun findLastLocation(): List<Double> {
        val list = mutableListOf<Double>()
        list.add(40.1903484)
        list.add(44.5148367)
        return list
    }
}