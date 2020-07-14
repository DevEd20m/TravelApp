package com.deved.myepxinperu.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.deved.data.repository.LocationRepository
import com.deved.interactors.SaveLocation
import com.deved.myepxinperu.data.dataBase.PlaceDataBase
import com.deved.myepxinperu.data.dataBase.RoomUserDataSource
import com.deved.myepxinperu.data.device.PlayServicesLocationDataSource

class SaveLocationWorker(private val context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        val dataSource = RoomUserDataSource(PlaceDataBase.getDatabaseInstance(applicationContext))
        val playServices = PlayServicesLocationDataSource(applicationContext)
        val database = SaveLocation(LocationRepository(dataSource, playServices))
        try {
            Log.d("TAG","Se ejecutó la tarea")
            database.invoke()
        } catch (e: Exception) {
            Log.d("TAG","Se ejecutó la tarea sin éxito: ${e.message.toString()}")
            return Result.failure()
        }
        return Result.success()
    }

    companion object {
        const val KEY_ACTIVITY = "KEY_ACTIVITY"
        val WORK_NAME = SaveLocationWorker::class.java.name
    }
}