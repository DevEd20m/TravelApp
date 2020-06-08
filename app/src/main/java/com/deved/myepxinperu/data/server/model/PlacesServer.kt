package com.deved.myepxinperu.data.server.model

data class PlacesServer(
    val name: String?,
    val description: String?,
    val pictureOne: String?,
    val pictureSecond: String?,
    val createAt: String?
) {
    constructor() : this(null, null, null, null, null)
}
