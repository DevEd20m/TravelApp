package com.deved.myepxinperu.data.server

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment
import com.deved.data.common.DataResponse
import com.deved.data.source.PictureDataSource
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageException
import kotlinx.coroutines.tasks.await

class FirebasePictureDataSource(
    private val application: Fragment?,
    private val firebaseStorage: FirebaseStorage
) : PictureDataSource {
    override fun fetchPicture() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        application?.startActivityForResult(intent, 1)
    }

    override suspend fun uploadPicture(uri: String):DataResponse<String> {
        return try {
            val uriConverted = Uri.parse(uri)
            val reference = firebaseStorage.reference.child("Department/${uriConverted.lastPathSegment}")
            val uri = reference.putFile(uriConverted).await().storage.downloadUrl.await()
            DataResponse.Success(uri.toString())

        }catch (e:StorageException){
            DataResponse.ExceptionError(e)
        }catch (e:Exception){
            DataResponse.ExceptionError(e)
        }
    }
}