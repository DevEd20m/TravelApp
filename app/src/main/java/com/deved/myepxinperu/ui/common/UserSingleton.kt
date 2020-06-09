package com.deved.myepxinperu.ui.common

import com.deved.myepxinperu.data.storage.SharedPreferenceDataSource

object UserSingleton {
    private var repository: SharedPreferenceDataSource? = null
    fun init(repository: SharedPreferenceDataSource) {
        this.repository = repository
    }

    fun getUid(): String = repository?.getString(Constant.PREF_USER_UID, "").toString()
    fun setUid(value: String?) = repository?.putString(Constant.PREF_USER_UID, value)
    fun getEmail(): String = repository?.getString(Constant.PREF_USER_EMAIL, "").toString()
    fun setEmail(value: String?) = repository?.putString(Constant.PREF_USER_EMAIL, value)
}