package com.deved.myepxinperu.data.server

import com.deved.data.common.DataResponse
import com.deved.data.source.PlaceDataSource
import com.deved.domain.Department
import com.deved.domain.Places
import com.deved.myepxinperu.R
import com.deved.myepxinperu.ui.common.UiContext
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.tasks.await

class FirebasePlacesDataSource(
    private val firebaseFirestore: FirebaseFirestore
) : PlaceDataSource {

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

    override suspend fun getDetailPlace(touristId: Int): DataResponse<Places> {
        return try {
            val result = firebaseFirestore.collection("Department")
                .document("AYACUCHO").collection("TouristDestination")
                .document("Wari").get().await()
            DataResponse.Success(result.toObject(Places::class.java))
        } catch (e: FirebaseFirestoreException) {
            DataResponse.ExceptionError(e)
        } catch (e: Exception) {
            DataResponse.ExceptionError(e)
        }
    }
}