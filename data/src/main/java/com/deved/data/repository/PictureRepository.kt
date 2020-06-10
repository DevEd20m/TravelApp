package com.deved.data.repository

import com.deved.data.common.DataResponse
import com.deved.data.repository.PermissionsChecker.Permissions.READ_EXTERNAL_STORAGE
import com.deved.data.repository.PermissionsChecker.Permissions.WRITE_EXTERNAL_STORAGE
import com.deved.data.source.PictureDataSource

class PictureRepository(
    private val pictureDataSource: PictureDataSource,
    private val permissionsChecker: PermissionsChecker
) {
    fun fetchPicture() {
        return if (permissionsChecker.checkPermission(READ_EXTERNAL_STORAGE)
            and permissionsChecker.checkPermission(WRITE_EXTERNAL_STORAGE)
        ) pictureDataSource.fetchPicture() else permissionsChecker.requestPermissionsStorage()
    }

    suspend fun uploadPicture(uri: String): DataResponse<String> {
        return pictureDataSource.uploadPicture(uri)
    }
}

interface PermissionsChecker {
    enum class Permissions { READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE, COARSE_LOCATION }

    fun checkPermission(permission: Permissions): Boolean
    fun requestPermissionsStorage()
    fun requestPermissionsLocation()
}