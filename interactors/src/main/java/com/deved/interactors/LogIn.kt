package com.deved.interactors

import com.deved.data.repository.SecurityRepository

class LogIn(private val securityRepository: SecurityRepository) {
    suspend fun invoke(user: String, password: String) = securityRepository.logIn(user, password)
}