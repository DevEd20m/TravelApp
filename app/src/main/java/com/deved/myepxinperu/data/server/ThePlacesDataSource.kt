package com.deved.myepxinperu.data.server

import com.deved.data.common.DataResponse
import com.deved.data.source.RemoteDataSource
import com.deved.domain.Department
import com.deved.domain.User
import com.deved.myepxinperu.R
import com.deved.myepxinperu.data.server.mapper.DepartmentMapper
import com.deved.myepxinperu.data.server.model.DepartmentServer
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
        try {
            val result = firebaseFirestore.collection("MyExpInPeru").get().await()
            val department = arrayListOf<Department>()
            result.forEach {
                department.add(it.toObject(Department::class.java))
            }
            return DataResponse.Success(department)
        } catch (e: FirebaseFirestoreException) {
            return DataResponse.ExceptionError(e)
        } catch (e: Exception) {
            return DataResponse.ExceptionError(e)
        }
    }

    override suspend fun registerExp(place: Department): DataResponse<String> {
        return try {
            val pictures = arrayListOf<String?>()
            pictures.add(place.pictures[0].picture)
            pictures.add(place.pictures[1].picture)

            val department = hashMapOf<String, Any?>()
            department["name"] = place.name
            department["description"] = place.description
            department["picture"] = pictures
            department["createAt"] = place.createAt

            firebaseFirestore.document("Department/${place.name.toUpperCase()}")
                .collection("TouristDestination")
                .document(place.name).set(department)
            DataResponse.Success(UiContext.getString(R.string.success_registered_shared))
        } catch (e: FirebaseFirestoreException) {
            DataResponse.ExceptionError(e)
        } catch (e: Exception) {
            DataResponse.ExceptionError(e)
        }
    }
}