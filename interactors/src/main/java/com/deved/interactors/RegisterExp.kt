package com.deved.interactors

import com.deved.data.repository.PlacesRepository
import com.deved.domain.Places

class RegisterExp (private val placesRepository: PlacesRepository){
    suspend fun invoke(place:Places)= placesRepository.registerExp(place)
}