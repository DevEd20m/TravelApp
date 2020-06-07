package com.deved.myepxinperu.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.deved.data.repository.PlacesRepository
import com.deved.domain.Department
import com.deved.interactors.GetAllPlaces
import com.deved.myepxinperu.data.server.FirebasePlacesDataSource
import com.deved.myepxinperu.databinding.FragmentHomeBinding
import com.deved.myepxinperu.ui.common.getViewModel
import com.deved.myepxinperu.ui.common.toast
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment() {
    private lateinit var firebaseFirestore: FirebaseFirestore
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewmodel: HomeViewModel
    private val adapter by lazy { HomeAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        setUpViewModel()
        viewmodel.fetchPlaces()
        attachViewModelObservers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewPlaces.adapter = adapter
    }

    private fun setUpViewModel() {
        viewmodel = getViewModel {
            firebaseFirestore = FirebaseFirestore.getInstance()
            val useCase = GetAllPlaces(PlacesRepository(FirebasePlacesDataSource(firebaseFirestore)))
            HomeViewModel(useCase)
        }
    }

    private fun attachViewModelObservers() {
        viewmodel.places.observe(viewLifecycleOwner, placesObserver)
        viewmodel.isViewLoading.observe(viewLifecycleOwner, isViewLoadingObserver)
        viewmodel.onMessageError.observe(viewLifecycleOwner, onMessageErrorObserver)
    }

    private val placesObserver = Observer<List<Department>> {
        it?.let { places ->
            adapter.places = it
        }
    }

    private val isViewLoadingObserver = Observer<Boolean> {
        binding.progressBarHome.isVisible = it
    }

    private val onMessageErrorObserver = Observer<Any> {
        activity?.toast(it.toString())
    }

    companion object {
        val TAG = HomeFragment::class.java.name
        fun newInstance() = HomeFragment()
    }
}
