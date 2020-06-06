package com.deved.myepxinperu.data.server.mapper

interface Mapper<O, I> {
    fun mapToEntity(type:I): O
}