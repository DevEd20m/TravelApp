package com.deved.interactors

import com.deved.data.repository.PictureRepository

class GetPicture(private val placesRepository: PictureRepository) {
     fun invoke() = placesRepository.fetchPicture()
}