package com.deved.myepxinperu.ui.mapper

import com.deved.domain.Places
import com.deved.myepxinperu.ui.model.PlacesView

class ListPlacesMapper : Mapper<List<Places>, List<PlacesView>> {
    override fun mapToEntity(type: List<PlacesView>): List<Places> {
        var list = arrayListOf<Places>()
        for (item in type) {
            list.add(Places(item.picture))
        }
        return list
    }

}