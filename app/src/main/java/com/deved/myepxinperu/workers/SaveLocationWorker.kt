package com.deved.myepxinperu.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.deved.interactors.SaveLocation
import org.koin.core.KoinComponent
import org.koin.core.inject

class SaveLocationWorker(context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams), KoinComponent {
    override suspend fun doWork(): Result {
        val database: SaveLocation by inject()
        try {
            Log.d("TAG", "Se ejecutó la tarea")
            database.invoke()
        } catch (e: Exception) {
            Log.d("TAG", "Se ejecutó la tarea sin éxito: ${e.message.toString()}")
            return Result.failure()
        }
        return Result.success()
    }

    companion object {
        const val KEY_ACTIVITY = "KEY_ACTIVITY"
        const val TIME_PERIODIC_TASK : Long = 15
        val WORK_NAME = SaveLocationWorker::class.java.name
    }
}