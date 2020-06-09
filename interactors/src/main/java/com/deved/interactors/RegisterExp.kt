package com.deved.interactors

import com.deved.data.repository.PlacesRepository
import com.deved.domain.Department

class RegisterExp (private val placesRepository: PlacesRepository){
    suspend fun invoke(place:Department,userId:String)= placesRepository.registerExp(place,userId)
}