package com.deved.myepxinperu.data.server

import com.deved.data.common.DataResponse
import com.deved.data.source.RemoteDataSource
import com.deved.domain.Department
import com.deved.domain.Places
import com.deved.domain.PlacesPicture
import com.deved.domain.User
import com.deved.myepxinperu.R
import com.deved.myepxinperu.ui.common.UiContext
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.tasks.await

class ThePlacesDataSource(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) : RemoteDataSource {
    override suspend fun logIn(user: String, password: String): DataResponse<String> {
        return try {
            firebaseAuth.signInWithEmailAndPassword(user, password).await()
            DataResponse.Success(UiContext.getString(R.string.success_auth))
        } catch (e: FirebaseAuthException) {
            DataResponse.ExceptionError(e)
        } catch (e: Exception) {
            DataResponse.ExceptionError(e)
        }
    }

    override suspend fun registerUser(user: User): DataResponse<String> {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(user.email, user.password).await()
            val userServer = hashMapOf<String, Any>()
            userServer["email"] = user.email
            userServer["name"] = user.name
            userServer["lastName"] = user.lastName
            firebaseFirestore.collection("Users").document(firebaseAuth.currentUser!!.uid)
                .set(userServer).await()
            DataResponse.Success(UiContext.getString(R.string.success_registered_user))
        } catch (e: FirebaseAuthException) {
            DataResponse.ExceptionError(e)
        } catch (e: FirebaseFirestoreException) {
            DataResponse.ExceptionError(e)
        } catch (e: Exception) {
            DataResponse.ExceptionError(e)
        }
    }

    override suspend fun fetchAllPlaces(): DataResponse<List<Department>> {
        return try {
            val result = firebaseFirestore.collectionGroup("TouristDestination").get().await()
            val department = arrayListOf<Department>()
            result.forEach {
                department.add(
                    Department(
                        null,
                        Places(
                            it.getString("name"),
                            it.getString("description"),
                            it.getString("pictureOne"),
                            it.getString("pictureSecond"),
                            it.getString("createAt")
                        )
                    )
                )
            }
            DataResponse.Success(department)
        } catch (e: FirebaseFirestoreException) {
            DataResponse.ExceptionError(e)
        } catch (e: Exception) {
            DataResponse.ExceptionError(e)
        }
    }

    override suspend fun registerExp(data: Department): DataResponse<String> {
        return try {
            val department = hashMapOf<String, Any?>()
            with(data.place!!) {
                department["name"] = name
                department["description"] = description
                department["pictureOne"] = picturesOne
                department["pictureSecond"] = picturesSecond
                department["createAt"] = createAt
            }

            firebaseFirestore.document("Department/${data.name!!.toUpperCase()}")
                .collection("TouristDestination")
                .document(data.place!!.name!!).set(department)
            DataResponse.Success(UiContext.getString(R.string.success_registered_shared))
        } catch (e: FirebaseFirestoreException) {
            DataResponse.ExceptionError(e)
        } catch (e: Exception) {
            DataResponse.ExceptionError(e)
        }
    }
}