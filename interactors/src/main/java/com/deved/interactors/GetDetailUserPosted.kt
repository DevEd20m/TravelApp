package com.deved.interactors

import com.deved.data.repository.PlacesRepository

class GetDetailUserPosted(private val useCase:PlacesRepository) {
    suspend fun invoke(userUid:String?) = useCase.getDetailUserPosted(userUid)
}