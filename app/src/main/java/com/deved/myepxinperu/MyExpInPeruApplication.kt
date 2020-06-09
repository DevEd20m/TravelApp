package com.deved.myepxinperu

import android.app.Application
import com.deved.myepxinperu.data.storage.SharedPreferenceDataSource
import com.deved.myepxinperu.ui.common.UiContext
import com.deved.myepxinperu.ui.common.UserSingleton

class MyExpInPeruApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        UiContext.init(applicationContext)
        UserSingleton.init(SharedPreferenceDataSource(applicationContext))
    }
}