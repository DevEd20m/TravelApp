package com.deved.myepxinperu.data.server.mapper

import com.deved.domain.Places
import com.deved.myepxinperu.data.server.model.PlacesServer

class PlacesMapper : Mapper<Places, PlacesServer> {
    override fun mapToEntity(type: PlacesServer?): Places {
        return Places(type?.name,type?.description,type?.pictureOne,type?.pictureSecond,type?.createAt)
    }
}