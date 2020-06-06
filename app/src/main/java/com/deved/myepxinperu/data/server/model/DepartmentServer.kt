package com.deved.myepxinperu.data.server.model

data class DepartmentServer(
    val name: String,
    val description: String,
    val pictures: List<PlacesServer>,
    val createAt: String
)