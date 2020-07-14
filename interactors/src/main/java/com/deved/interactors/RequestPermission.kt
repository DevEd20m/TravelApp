package com.deved.interactors

import com.deved.data.repository.LocationRepository

class RequestPermission(private val locationRepository: LocationRepository) {
    fun responseInvoke() = locationRepository.validatePermission()
    fun requestInvoke() = locationRepository.requestPermission()
}