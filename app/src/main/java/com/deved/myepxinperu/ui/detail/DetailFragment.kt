package com.deved.myepxinperu.ui.detail

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import coil.api.load
import com.deved.data.repository.PlacesRepository
import com.deved.domain.Places
import com.deved.domain.User
import com.deved.interactors.GetDetailPlace
import com.deved.interactors.GetDetailUserPosted
import com.deved.myepxinperu.data.server.FirebasePlacesDataSource
import com.deved.myepxinperu.databinding.FragmentDetailBinding
import com.deved.myepxinperu.ui.common.UserSingleton
import com.deved.myepxinperu.ui.common.getViewModel
import com.deved.myepxinperu.ui.common.toast
import com.google.firebase.firestore.FirebaseFirestore

class DetailFragment : Fragment() {
    private lateinit var fireStore: FirebaseFirestore
    private lateinit var viewmodel: DetailViewModel
    private lateinit var binding: FragmentDetailBinding
    private var departmentName: String? = null
    private var placeName: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        departmentName = arguments?.getString(mDepartmentName, "")
        placeName = arguments?.getString(mPlaceName, "")
        setUpViewModel()
        viewmodel.getDetailPlace(departmentName!!,placeName!!)
        viewmodel.getDetailUserPosted(UserSingleton.getUid())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        setUpViewModelObservers()
        return binding.root
    }

    private fun setUpViewModel() {
        viewmodel = getViewModel {
            fireStore = FirebaseFirestore.getInstance()
            val useCase = GetDetailPlace(PlacesRepository(FirebasePlacesDataSource(fireStore)))
            val userPosted = GetDetailUserPosted(PlacesRepository(FirebasePlacesDataSource(fireStore)))
            DetailViewModel(useCase,userPosted)
        }
    }

    private fun setUpViewModelObservers() {
        viewmodel.isViewLoading.observe(this, isViewLoadingObserver)
        viewmodel.onErrorMessage.observe(this, onErrorMessageObserver)
        viewmodel.onSuccessMessage.observe(this, onSuccessMessageObserver)
        viewmodel.place.observe(this, placeObserver)
        viewmodel.userPosted.observe(this, userPostedObserver)
    }

    private val isViewLoadingObserver = Observer<Boolean> {
        binding.progressBarDetail.isVisible = it
    }
    private val onErrorMessageObserver = Observer<Any> {
        activity?.toast(it.toString())
    }
    private val onSuccessMessageObserver = Observer<Any> {
        activity?.toast(it.toString())
    }
    private val placeObserver = Observer<Places> {
        with(binding){
            imageViewBackgroundDetail.load(it.picturesOne)
            textViewDatePublished.text = it.createAt
            textViewDescriptionPlace.text = it.description
            setUpToolbar(it.name)
        }
    }
    private val userPostedObserver = Observer<User> {
        with(binding){
            textViewNameAvatar.text = it.name?.plus(" ").plus(it.lastName)
        }
    }

    private fun setUpToolbar(tit:String?){
        binding.toolbarDetail.setTitle(tit)
        binding.toolbarDetail.setTitleTextColor(Color.WHITE)
    }

    companion object {
        @JvmStatic
        fun newInstance(departmentName:String?,placeName: String?) = DetailFragment().apply {
            arguments = Bundle().apply {
                putString(mDepartmentName, departmentName)
                putString(mPlaceName, placeName)
            }
        }

        private const val mDepartmentName = "mDepartmentName"
        private const val mPlaceName = "mPlaceName"
    }
}
