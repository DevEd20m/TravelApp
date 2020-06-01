package com.deved.data.repository

import com.deved.data.repository.PermissionsChecker.Permissions.READ_EXTERNAL_STORAGE
import com.deved.data.repository.PermissionsChecker.Permissions.WRITE_EXTERNAL_STORAGE
import com.deved.data.source.PictureDataSource

class PictureRepository(
    private val pictureDataSource: PictureDataSource,
    private val permissionsChecker: PermissionsChecker
) {
    fun fetchPicture() {
        return if (permissionsChecker.check(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE)) {
            pictureDataSource.fetchPicture()
        } else {
            permissionsChecker.requestPermissionsChecker()
        }
    }
}

interface PermissionsChecker {
    enum class Permissions { READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE }

    fun check(permission: Permissions, writeExternalStorage: Permissions): Boolean
    fun requestPermissionsChecker()
}