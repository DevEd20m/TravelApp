package com.deved.myepxinperu.ui.mapper

import com.deved.domain.Department
import com.deved.myepxinperu.ui.model.DepartmentView

class DepartmentViewMapperView :Mapper<DepartmentView,Department>{
    override fun mapToEntity(type: Department): DepartmentView {
        return DepartmentView(type.name,type.description,ListPlacesViewMapperView().mapToEntity(type.pictures),type.createAt)
    }

}