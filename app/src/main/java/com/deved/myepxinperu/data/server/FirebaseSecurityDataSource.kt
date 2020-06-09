package com.deved.myepxinperu.data.server

import com.deved.data.common.DataResponse
import com.deved.data.source.SecurityDataSource
import com.deved.domain.User
import com.deved.myepxinperu.R
import com.deved.myepxinperu.ui.common.UiContext
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.tasks.await

class FirebaseSecurityDataSource(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) : SecurityDataSource {
    override suspend fun logIn(user: String, password: String): DataResponse<String> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(user, password).await()
            result.user?.uid
            DataResponse.Success(result.user?.uid)
        } catch (e: FirebaseAuthException) {
            DataResponse.ExceptionError(e)
        } catch (e: Exception) {
            DataResponse.ExceptionError(e)
        }
    }

    override suspend fun getProfile(userId: String?): DataResponse<User> {
        return try {
            val result = firebaseFirestore.collection("Users")
                .document(userId.toString()).get().await()
            val user = User(
                result.id,
                result.getString("name"),
                result.getString("lastName"),
                result.getString("email"),
                null, null
            )
            DataResponse.Success(user)
        } catch (e: FirebaseFirestoreException) {
            DataResponse.ExceptionError(e)
        } catch (e: Exception) {
            DataResponse.ExceptionError(e)
        }
    }

    override suspend fun registerUser(user: User): DataResponse<String> {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(
                user.email.toString(),
                user.password.toString()
            ).await()
            val userServer = hashMapOf<String, Any>()
            userServer["email"] = user.email.toString()
            userServer["name"] = user.name.toString()
            userServer["lastName"] = user.lastName.toString()
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
}