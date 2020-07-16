package com.deved.myepxinperu

import android.app.Application
import android.util.Log
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.deved.myepxinperu.data.storage.SharedPreferenceDataSource
import com.deved.myepxinperu.di.initDI
import com.deved.myepxinperu.ui.common.UiContext
import com.deved.myepxinperu.ui.common.UserSingleton
import com.deved.myepxinperu.workers.SaveLocationWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class MyExpInPeruApplication : Application() {
    private val applicationScope = CoroutineScope(Dispatchers.Default)
    override fun onCreate() {
        super.onCreate()
        initDI()
        UiContext.init(applicationContext)
        UserSingleton.init(SharedPreferenceDataSource(applicationContext))
        runProcess()
    }

    fun runProcess() {
        applicationScope.launch {
            if (UserSingleton.getUid() != null) {
                Log.d("TAG", "Proceso en ejecución")
                setUpWorker()
            }
        }
    }

    private fun setUpWorker() {
        val repeatingRequest = PeriodicWorkRequestBuilder<SaveLocationWorker>(15, TimeUnit.MINUTES)
            .build()
        WorkManager.getInstance(this).enqueue(repeatingRequest)
    }
}