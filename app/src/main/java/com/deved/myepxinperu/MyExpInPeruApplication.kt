package com.deved.myepxinperu

import android.app.Application
import com.deved.myepxinperu.ui.common.UiContext

class MyExpInPeruApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        UiContext.init(applicationContext)
    }
}