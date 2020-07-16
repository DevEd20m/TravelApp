package com.deved.data.repository

import com.deved.data.common.DataResponse
import com.deved.data.source.PictureDataSource

class PictureRepositoryImpl(
    private val pictureDataSource: PictureDataSource
) : PictureRepository {

    override suspend fun uploadPicture(uri: String): DataResponse<String> {
        return pictureDataSource.uploadPicture(uri)
    }
}

interface PermissionsChecker {
    enum class Permissions { READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE, COARSE_LOCATION }

    fun checkPermission(permission: Permissions): Boolean
}