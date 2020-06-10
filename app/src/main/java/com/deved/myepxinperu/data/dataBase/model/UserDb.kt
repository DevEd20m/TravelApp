package com.deved.myepxinperu.data.dataBase.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserDb(
    @PrimaryKey(autoGenerate = true)
    val id: String?,
    @ColumnInfo(name = "longitude")
    val longitude: Double?,
    @ColumnInfo(name = "latitude")
    val latitude: Double?
)