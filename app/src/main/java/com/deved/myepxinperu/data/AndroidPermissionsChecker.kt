package com.deved.myepxinperu.data

import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.deved.data.repository.PermissionsChecker

class AndroidPermissionsChecker(private val application: Activity?):PermissionsChecker {
    override fun check(
        permission: PermissionsChecker.Permissions,
        writeExternalStorage: PermissionsChecker.Permissions
    ): Boolean = ContextCompat.checkSelfPermission(application!!,permission.toAndroidId())== PackageManager.PERMISSION_GRANTED

    override fun requestPermissionsChecker() {
        ActivityCompat.requestPermissions(application!!, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE),10)
    }

}

private fun PermissionsChecker.Permissions.toAndroidId() = when (this) {
    PermissionsChecker.Permissions.READ_EXTERNAL_STORAGE -> Manifest.permission.READ_EXTERNAL_STORAGE
    PermissionsChecker.Permissions.WRITE_EXTERNAL_STORAGE -> Manifest.permission.WRITE_EXTERNAL_STORAGE
}