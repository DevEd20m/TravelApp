package com.deved.myepxinperu.data.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.deved.myepxinperu.data.dataBase.dao.UserDao
import com.deved.myepxinperu.data.dataBase.model.UserDb

private const val DATA_BASE = "appDatabase.db"
@Database(entities = [UserDb::class], version = 1)
abstract class PlaceDataBase : RoomDatabase() {

    companion object {
        private var INSTANCE: PlaceDataBase? = null
        fun getDatabaseInstance(context: Context): PlaceDataBase {
            return INSTANCE ?: synchronized(PlaceDataBase::class) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PlaceDataBase::class.java,
                    DATA_BASE
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }

    abstract fun userDao(): UserDao
}