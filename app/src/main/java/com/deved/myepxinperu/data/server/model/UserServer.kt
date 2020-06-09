package com.deved.myepxinperu.data.server.model

data class UserServer(
    val id: String?,
    val name: String?,
    val lastName: String?,
    val email: String?
) {
    constructor() : this(null, null, null, null)
}