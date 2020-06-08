package com.deved.interactors

import com.deved.data.repository.PlacesRepository

class GetAllDepartment(private val useCase: PlacesRepository) {
    suspend fun invoke() = useCase.getAllDepartment()
}