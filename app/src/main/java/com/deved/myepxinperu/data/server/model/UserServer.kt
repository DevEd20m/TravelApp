package com.deved.myepxinperu.data.server.model

data class UserServer(
    val id: String?,
    val name: String?,
    val lastName: String?,
    val email: String?,
    val password: String?,
    val location: String?
){
    constructor(id: String) : this(id,null,null,null,null,null)
}