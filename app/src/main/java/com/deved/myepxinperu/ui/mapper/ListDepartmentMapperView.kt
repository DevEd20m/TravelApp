package com.deved.myepxinperu.ui.mapper

import com.deved.domain.Department
import com.deved.myepxinperu.ui.model.DepartmentView

class ListDepartmentMapperView :Mapper<List<DepartmentView>,List<Department>>{
    override fun mapToEntity(type: List<Department>): List<DepartmentView> {
        val list = mutableListOf<DepartmentView>()
         for(item in type){
             DepartmentMapper().mapToEntity(DepartmentView(item.name,item.description,ListPlacesViewMapperView().mapToEntity(item.pictures),item.createAt))
         }
        list.addAll(list)
        return list
    }

}