package com.deved.myepxinperu.data

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.deved.data.repository.PermissionsChecker

class AndroidPermissionsChecker(private val application: Application?) : PermissionsChecker {
    override fun checkPermission(permission: PermissionsChecker.Permissions): Boolean =
        ContextCompat.checkSelfPermission(
            application!!,
            permission.toAndroidId()
        ) == PackageManager.PERMISSION_GRANTED
}

private fun PermissionsChecker.Permissions.toAndroidId() = when (this) {
    PermissionsChecker.Permissions.READ_EXTERNAL_STORAGE -> Manifest.permission.READ_EXTERNAL_STORAGE
    PermissionsChecker.Permissions.WRITE_EXTERNAL_STORAGE -> Manifest.permission.WRITE_EXTERNAL_STORAGE
    PermissionsChecker.Permissions.COARSE_LOCATION -> Manifest.permission.ACCESS_COARSE_LOCATION

}