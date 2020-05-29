package com.deved.domain

data class User(
    val id: String?,
    val name: String,
    val lastName: String,
    val email: String,
    val password: String,
    val location: String?
)