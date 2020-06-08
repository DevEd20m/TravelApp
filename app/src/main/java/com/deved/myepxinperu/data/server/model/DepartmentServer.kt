package com.deved.myepxinperu.data.server.model

data class DepartmentServer(
    val name: String?,
    val place: PlacesServer?
){
    constructor():this(null,null)
}