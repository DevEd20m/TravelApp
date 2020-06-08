package com.deved.data.source

import com.deved.data.common.DataResponse
import com.deved.domain.Department
import com.deved.domain.Places
import com.deved.domain.User

interface PlaceDataSource {
    suspend fun fetchAllPlaces(): DataResponse<List<Places>>
    suspend fun registerExp(place:Department):DataResponse<String>
    suspend fun getDetailPlace(placeName:String):DataResponse<Places>
}