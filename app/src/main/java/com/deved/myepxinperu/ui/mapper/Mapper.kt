package com.deved.myepxinperu.ui.mapper

interface Mapper<O, I> {
    fun mapToEntity(type: I): O
}