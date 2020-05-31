package com.deved.myepxinperu.ui.share

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.deved.data.repository.PlacesRepository
import com.deved.interactors.RegisterExp

import com.deved.myepxinperu.R
import com.deved.myepxinperu.data.server.DataSource
import com.deved.myepxinperu.databinding.FragmentShareBinding
import com.deved.myepxinperu.ui.common.getViewModel
import com.deved.myepxinperu.ui.common.toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ShareFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var binding :FragmentShareBinding
    private lateinit var viewModel: ShareViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShareBinding.inflate(inflater,container,false)
        setUpViewModel()
        setUpViewModelObserver()
        setUpEvents()
        return binding.root
    }

    private fun setUpViewModel() {
        viewModel = getViewModel {
            auth = FirebaseAuth.getInstance()
            firestore = FirebaseFirestore.getInstance()
            val useCase = RegisterExp(PlacesRepository(DataSource(auth,firestore)))
            ShareViewModel(useCase)
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
            val firstPicture = binding.textInputEditTextFirstPicture.text?.trim().toString()
            val secondPicture = binding.textInputEditSecondPicture.text?.trim().toString()
            viewModel.validateRegisterExp(description,firstPicture,secondPicture,department)
        }
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


    companion object {
        val TAG = ShareFragment::class.java.name
        fun newInstance() = ShareFragment()
    }
}
