package com.deved.myepxinperu.ui.model

data class PlacesView(
    val name: String?,
    val description:String?,
    val picture: List<PlacesPictureView>,
    val createAt: String
)