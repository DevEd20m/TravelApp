package com.deved.interactors

import com.deved.data.repository.PlacesRepository

class GetAllPlaces(private val placesRepository: PlacesRepository) {
    suspend fun invoke() = placesRepository.getAllPlaces()
}