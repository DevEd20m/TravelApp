package com.deved.myepxinperu.data

import android.content.Intent
import androidx.fragment.app.Fragment
import com.deved.data.source.PictureDataSource

class CameraServiceDataSource(private val application : Fragment?) :PictureDataSource {
    override fun fetchPicture() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        application?.startActivityForResult(intent,1)
    }
}