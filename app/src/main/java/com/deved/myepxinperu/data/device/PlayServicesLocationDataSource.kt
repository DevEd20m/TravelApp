package com.deved.myepxinperu.data.device

import android.content.Context
import com.deved.data.source.LocationDataSource

class PlayServicesLocationDataSource(application: Context) : LocationDataSource {
    override suspend fun findLastLocation(): List<Double> {
        // todo(Reemplazar por la locación real del dispositivo)
        val list = mutableListOf<Double>()
        list.add(40.1903484)
        list.add(44.5148367)
        return list
    }
}