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
}