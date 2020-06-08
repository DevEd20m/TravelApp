package com.deved.interactors

import com.deved.data.repository.PlacesRepository

class GetDetailPlace(private val dataSource:PlacesRepository) {
    suspend fun invoke(touristId:Int) = dataSource.getDetailPlace(touristId)
}