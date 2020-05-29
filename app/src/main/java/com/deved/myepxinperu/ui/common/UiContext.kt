package com.deved.myepxinperu.ui.common

import android.content.Context

object UiContext {
    private var context: Context? = null

    fun init(context: Context) {
        this.context = context
    }

    fun getString(id: Int) = context?.getString(id) ?: ""
}