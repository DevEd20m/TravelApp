package com.deved.myepxinperu.data

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.deved.data.repository.PermissionsChecker

class AndroidPermissionsChecker(private val application: Activity?):PermissionsChecker {
    override fun checkPermission(permission: PermissionsChecker.Permissions): Boolean
            = ContextCompat.checkSelfPermission(application!!,permission.toAndroidId())  == PackageManager.PERMISSION_GRANTED

    override fun requestPermissionsStorage() {
        ActivityCompat.requestPermissions(application!!, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE),10)
    }

    override fun requestPermissionsLocation() {
        ActivityCompat.requestPermissions(application!!, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),20)
    }

}

private fun PermissionsChecker.Permissions.toAndroidId() = when (this) {
    PermissionsChecker.Permissions.READ_EXTERNAL_STORAGE -> Manifest.permission.READ_EXTERNAL_STORAGE
    PermissionsChecker.Permissions.WRITE_EXTERNAL_STORAGE -> Manifest.permission.WRITE_EXTERNAL_STORAGE
    PermissionsChecker.Permissions.COARSE_LOCATION -> Manifest.permission.ACCESS_COARSE_LOCATION
}