package com.deved.myepxinperu.ui.mapper

import com.deved.domain.Department
import com.deved.myepxinperu.ui.model.DepartmentView

class DepartentViewMapperView :Mapper<DepartmentView,Department>{
    override fun mapToEntity(type: Department): DepartmentView {
        return DepartmentView(type.name,type.description,ListPlacesMapper().mapToEntity(),type.createAt)
    }

}