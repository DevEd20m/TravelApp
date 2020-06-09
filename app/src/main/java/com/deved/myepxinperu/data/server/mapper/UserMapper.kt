package com.deved.myepxinperu.data.server.mapper

import com.deved.domain.User
import com.deved.myepxinperu.data.server.model.UserServer

class UserMapper :Mapper<User,UserServer>{
    override fun mapToEntity(type: UserServer?): User {
        return User(type?.id,type?.name,type?.lastName,type?.email,null,null)
    }

}