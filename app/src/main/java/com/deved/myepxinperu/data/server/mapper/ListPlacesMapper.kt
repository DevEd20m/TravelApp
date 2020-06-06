package com.deved.myepxinperu.data.server.mapper

import com.deved.domain.Places
import com.deved.myepxinperu.data.server.model.PlacesServer

class ListPlacesMapper : Mapper<List<Places>, List<PlacesServer>> {
    override fun mapToEntity(type: List<PlacesServer>): List<Places> {
        val list = arrayListOf<Places>()
        for (item: PlacesServer in type) {
            list.add(Places(item.picture))
        }
        return list
    }
}
