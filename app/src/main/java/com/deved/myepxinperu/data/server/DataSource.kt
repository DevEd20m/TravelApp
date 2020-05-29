package com.deved.myepxinperu.data.server

import com.deved.data.common.DataResponse
import com.deved.data.source.RemoteDataSource
import com.deved.domain.Places
import com.deved.domain.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.tasks.await

data class DataSource(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) : RemoteDataSource {
    override suspend fun logIn(user: String, password: String): DataResponse<String> {
        return try {
            firebaseAuth.signInWithEmailAndPassword(user, password).await()
            DataResponse.Success("OK")
        } catch (e: FirebaseAuthException) {
            DataResponse.ExceptionError(e)
        } catch (e: Exception) {
            DataResponse.ExceptionError(e)
        }
    }

    override suspend fun registerUser(user:User): DataResponse<String> {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(user.email,user.password).await()
            val userServer = hashMapOf<String,Any>()
            userServer["email"] = user.email
            userServer["name"] = user.name
            userServer["lastName"] = user.lastName
            firebaseFirestore.collection("Users").document(firebaseAuth.currentUser!!.uid).set(userServer).await()
            DataResponse.Success("OK")
        } catch (e: FirebaseAuthException) {
            DataResponse.ExceptionError(e)
        } catch (e: FirebaseFirestoreException) {
            DataResponse.ExceptionError(e)
        } catch (e: Exception) {
            DataResponse.ExceptionError(e)
        }
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