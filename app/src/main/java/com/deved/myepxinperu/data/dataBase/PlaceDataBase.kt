package com.deved.myepxinperu.data.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.deved.myepxinperu.data.dataBase.dao.UserDao
import com.deved.myepxinperu.data.dataBase.model.UserDb

@Database(entities = [UserDb::class], version = 1)
abstract class PlaceDataBase : RoomDatabase() {

    companion object {
        fun getDatabaseInstance(context: Context): PlaceDataBase {
            return Room.databaseBuilder(
                context.applicationContext,
                PlaceDataBase::class.java,
                "PlaceDataBase.bd"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    abstract fun userDao(): UserDao
}