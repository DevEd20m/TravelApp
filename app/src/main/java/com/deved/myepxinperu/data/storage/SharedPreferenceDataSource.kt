package com.deved.myepxinperu.data.storage

import android.content.Context
import android.content.SharedPreferences
import com.deved.data.source.PreferenceDataSource
import com.deved.myepxinperu.ui.common.Constant
import com.deved.myepxinperu.ui.common.get
import com.deved.myepxinperu.ui.common.put

class SharedPreferenceDataSource(private val application: Context) : PreferenceDataSource {
    private val sharedPreferences: SharedPreferences by lazy {
        application.getSharedPreferences(Constant.PREF_NAME, Constant.PRIVATE_MODE)
    }

    fun getString(key: String, default: String): String {
        return sharedPreferences.get(key, default)
    }

    fun putString(key: String, value: String?) {
        sharedPreferences.put(key, value)
    }
}