package com.deved.myepxinperu.ui.mapper

import com.deved.domain.Places
import com.deved.myepxinperu.ui.model.PlacesView

class ListPlacesViewMapperView :Mapper<List<PlacesView>,List<Places>>{
    override fun mapToEntity(type: List<Places>): List<PlacesView> {
        val list = arrayListOf<PlacesView>()
        for (item in type){
            list.add(PlacesView(item.picture))
        }
        return list
    }

}