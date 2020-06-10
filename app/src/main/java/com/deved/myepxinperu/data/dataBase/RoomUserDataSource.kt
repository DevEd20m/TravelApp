package com.deved.myepxinperu.data.dataBase

import com.deved.data.source.DataBaseDataSource
import com.deved.myepxinperu.data.dataBase.model.UserDb

class RoomUserDataSource(private val room: PlaceDataBase) : DataBaseDataSource {
    override suspend fun saveLocation(longitude: Double, latitude: Double) {
        room.userDao().saveLocation(UserDb(null, longitude, latitude))
    }

}