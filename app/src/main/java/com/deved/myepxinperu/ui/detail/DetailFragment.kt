package com.deved.myepxinperu.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.deved.data.repository.PlacesRepository
import com.deved.interactors.GetDetailPlace
import com.deved.myepxinperu.data.server.FirebasePlacesDataSource
import com.deved.myepxinperu.databinding.FragmentDetailBinding
import com.deved.myepxinperu.ui.common.getViewModel
import com.deved.myepxinperu.ui.common.toast
import com.google.firebase.firestore.FirebaseFirestore

class DetailFragment : Fragment() {
    private lateinit var fireStore: FirebaseFirestore
    private lateinit var viewmodel: DetailViewModel
    private lateinit var binding: FragmentDetailBinding
    private var touristId: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        touristId = arguments?.getInt(mTouristId, 0)
        viewmodel = getViewModel {
            fireStore = FirebaseFirestore.getInstance()
            val useCase = GetDetailPlace(PlacesRepository(FirebasePlacesDataSource(fireStore)))
            DetailViewModel(useCase)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        setUpViewModelObservers()
        return binding.root
    }

    private fun setUpViewModelObservers() {
        viewmodel.isViewLoading.observe(this, isViewLoadingObserver)
        viewmodel.onErrorMessage.observe(this, onErrorMessageObserver)
        viewmodel.onSuccessMessage.observe(this, onSuccessMessageObserver)

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

    companion object {
        @JvmStatic
        fun newInstance(touristId: Int) = DetailFragment().apply {
            arguments = Bundle().apply {
                putInt(mTouristId, touristId)
            }
        }

        private const val mTouristId = "mTouristId"
    }
}
