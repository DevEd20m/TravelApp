package com.deved.interactors

import com.deved.data.repository.PictureRepository

class UploadPicture (private val pictureRepository: PictureRepository){
    suspend fun invoke(uri:String) = pictureRepository.uploadPicture(uri)
}