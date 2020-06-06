package com.deved.myepxinperu.ui.model

data class DepartmentView(
    val name: String,
    val description: String,
    val pictures: List<PlacesView>,
    val createAt: String
)