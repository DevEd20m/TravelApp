package com.deved.myepxinperu.data.server.mapper

import com.deved.domain.Places
import com.deved.myepxinperu.data.server.model.PlacesServer

class PlacesMapper : Mapper<Places, PlacesServer> {
    override fun mapToEntity(type: PlacesServer): Places {
        return Places(type.picture)
    }
}