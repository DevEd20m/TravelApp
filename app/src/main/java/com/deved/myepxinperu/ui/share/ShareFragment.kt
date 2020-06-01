package com.deved.myepxinperu.ui.share

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.deved.data.repository.PictureRepository
import com.deved.data.repository.PlacesRepository
import com.deved.interactors.GetPicture
import com.deved.interactors.RegisterExp
import com.deved.interactors.UploadPicture
import com.deved.myepxinperu.data.AndroidPermissionsChecker
import com.deved.myepxinperu.data.server.ThePictureDataSource

import com.deved.myepxinperu.data.server.ThePlacesDataSource
import com.deved.myepxinperu.databinding.FragmentShareBinding
import com.deved.myepxinperu.ui.common.getViewModel
import com.deved.myepxinperu.ui.common.toast
import com.deved.myepxinperu.ui.model.Picture
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class ShareFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var storage: FirebaseStorage
    private lateinit var firestore: FirebaseFirestore
    private lateinit var binding :FragmentShareBinding
    private lateinit var viewModel: ShareViewModel
    private lateinit var picturesArray :MutableList<Picture>
    private val adapter  by lazy { ShareAdapter(getListener()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShareBinding.inflate(inflater,container,false)
        setUpViewModel()
        setUpRecyclerView()
        setUpViewModelObserver()
        setUpEvents()
        return binding.root
    }

    private fun setUpRecyclerView() {
        binding.recyclerViewPictures.layoutManager = GridLayoutManager(activity,2)
        binding.recyclerViewPictures.adapter = adapter
        picturesArray = mutableListOf()
    }

    private fun setUpViewModel() {
        viewModel = getViewModel {
            auth = FirebaseAuth.getInstance()
            storage = FirebaseStorage.getInstance()
            firestore = FirebaseFirestore.getInstance()
            val useCase = RegisterExp(PlacesRepository(ThePlacesDataSource(auth,firestore)))
            val pictureRepository = PictureRepository(ThePictureDataSource(this,storage),AndroidPermissionsChecker(activity))
            val useCasePicture = GetPicture(pictureRepository)
            val uploadPicture = UploadPicture(pictureRepository)
            ShareViewModel(useCase,useCasePicture,uploadPicture)
        }
    }

    private fun setUpViewModelObserver() {
        viewModel.isViewLoading.observe(this,isViewLoadingObserver)
        viewModel.onMessageError.observe(this,onMessageErrorObserver)
        viewModel.onMessageSuccess.observe(this,onMessageSuccessObserver)
    }

    private fun setUpEvents() {
        binding.materialButtonShareExp.setOnClickListener {
            val description = binding.textInputEditTextDescription.text?.trim().toString()
            val department = binding.textInputEditDepartment.text?.trim().toString()
            val one = picturesArray[0]
            val second = picturesArray[1]
            viewModel.validateRegisterExp(description,one.picture.toString(),second.picture.toString(),department)
        }

        binding.imageButtonAdd.setOnClickListener {
            viewModel.getPicture()
        }
    }

    private fun getListener(): (Picture) -> Unit {
        return{
            deletePicture(it)
        }
    }

    private fun deletePicture(item:Picture) {
        picturesArray.remove(item)
        adapter.setData(picturesArray)
    }

    private val isViewLoadingObserver = Observer<Boolean>{
        binding.progressBarShareExp.isVisible = it
    }

    private val onMessageErrorObserver = Observer<Any>{
        activity?.toast(it.toString())
    }

    private val onMessageSuccessObserver = Observer<Any>{
        activity?.toast(it.toString())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 1) {
            picturesArray.add(Picture(data?.data,"Imagen2","Descripción2"))
            adapter.setData(picturesArray)
        }
    }

    companion object {
        val TAG = ShareFragment::class.java.name
        fun newInstance() = ShareFragment()
    }
}
