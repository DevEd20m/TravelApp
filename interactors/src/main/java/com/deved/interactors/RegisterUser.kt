package com.deved.interactors

import com.deved.data.repository.SecurityRepository
import com.deved.domain.User

class RegisterUser(private val securityRepository: SecurityRepository) {
    suspend fun invoke(user: User) = securityRepository.registerUser(user)
}