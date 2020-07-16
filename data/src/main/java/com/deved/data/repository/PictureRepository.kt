package com.deved.data.repository

import com.deved.data.common.DataResponse

interface PictureRepository {
    suspend fun uploadPicture(uri: String): DataResponse<String>
}