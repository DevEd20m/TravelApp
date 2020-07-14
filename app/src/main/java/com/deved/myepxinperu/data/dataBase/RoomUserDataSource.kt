package com.deved.myepxinperu.data.dataBase

import com.deved.data.source.DataBaseDataSource
import com.deved.myepxinperu.data.dataBase.model.UserDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomUserDataSource(private val room: PlaceDataBase) : DataBaseDataSource {
    override suspend fun saveLocation(longitude: Double, latitude: Double) = withContext(Dispatchers.IO){
        room.userDao().saveLocation(UserDb(null, longitude, latitude))
    }
}