package com.deved.interactors

import com.deved.data.repository.PlacesRepository

class GetDetailPlace(private val dataSource:PlacesRepository) {
    suspend fun invoke(placeName:String) = dataSource.getDetailPlace(placeName)
}