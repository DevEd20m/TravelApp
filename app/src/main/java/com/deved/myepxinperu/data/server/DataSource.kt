package com.deved.myepxinperu.data.server

import android.util.Log
import com.deved.data.common.DataResponse
import com.deved.data.source.RemoteDataSource
import com.deved.domain.Places
import com.deved.domain.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.tasks.await

data class DataSource(
    private val firebaseFirestore: FirebaseFirestore
) : RemoteDataSource {
    override suspend fun logIn(user: String, password: String): DataResponse<User> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//        return try {
//            firebaseFirestore.collection("Users")
//            val result = firebaseAuth.currentUser
//            val user = User(null,result)
//            return DataResponse.Success(result)
//        } catch (e: Exception) {
//            return DataResponse.ExceptionError(e)
//        }
    }

    override suspend fun registerUser(): DataResponse<User> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun fetchAllPlaces(): DataResponse<List<Places>> {
        try {
            val result = firebaseFirestore.collection("MyExpInPeru").get().await()
            val places = arrayListOf<Places>()
            result.forEach {
                places.add(
                    Places(
                        it.getString("Id")?.toInt(),
                        it.getString("description"),
                        it.getString("picture"),
                        null, null, null
                    )
                )
            }
            return DataResponse.Success(places)
        } catch (e: FirebaseFirestoreException) {
            return DataResponse.ExceptionError(e)
        } catch (e: Exception) {
            return DataResponse.ExceptionError(e)
        }
    }
}