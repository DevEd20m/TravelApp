package com.deved.domain

data class Department(
    val name: String,
    val description: String,
    val pictures: List<Places>,
    val createAt: String
)
