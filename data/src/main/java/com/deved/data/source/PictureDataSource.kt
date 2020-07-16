package com.deved.data.source

import com.deved.data.common.DataResponse

interface PictureDataSource {
    suspend fun uploadPicture(uri: String):DataResponse<String>
}