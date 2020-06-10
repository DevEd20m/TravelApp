package com.deved.myepxinperu.data.dataBase.dao

import androidx.room.Dao
import androidx.room.Insert
import com.deved.myepxinperu.data.dataBase.model.UserDb

@Dao
interface UserDao {
    @Insert()
    fun saveLocation(user: UserDb)
}