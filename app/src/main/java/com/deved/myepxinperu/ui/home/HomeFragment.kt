package com.deved.myepxinperu.ui.home

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.deved.domain.Department
import com.deved.myepxinperu.databinding.FragmentHomeBinding
import com.deved.myepxinperu.ui.common.PermissionRequester
import com.deved.myepxinperu.ui.common.RequestPermission
import com.deved.myepxinperu.ui.common.toast
import org.koin.android.scope.lifecycleScope
import org.koin.android.viewmodel.scope.viewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewmodel: HomeViewModel by lifecycleScope.viewModel(this)
    private val adapter by lazy { HomeAdapter(getDepartment()) }
    private lateinit var listener: HomeFragmentListener
    private lateinit var requestManager: PermissionRequester

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        viewmodel.fetchDepartment()
        attachViewModelObservers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewPlaces.adapter = adapter
        requestManager = PermissionRequester(requireActivity())
        viewmodel.requestPermission()
    }

    private fun attachViewModelObservers() {
        viewmodel.places.observe(viewLifecycleOwner, placesObserver)
        viewmodel.isViewLoading.observe(viewLifecycleOwner, isViewLoadingObserver)
        viewmodel.onMessageError.observe(viewLifecycleOwner, onMessageErrorObserver)
        viewmodel.isPermissionGaranted.observe(viewLifecycleOwner, isPermissionGrantedObserver)
    }

    private val placesObserver = Observer<List<Department>> {
        it?.let { places ->
            adapter.places = it
        }
    }

    private val isViewLoadingObserver = Observer<Boolean> {
        binding.progressBarHome.isVisible = it
    }

    private val isPermissionGrantedObserver = Observer<RequestPermission> {
        when (it) {
            is RequestPermission.RequestLocation -> {
                requestManager.request(Manifest.permission.ACCESS_COARSE_LOCATION, ::handleGarantedPermission)
            }
        }
    }

    private val onMessageErrorObserver = Observer<Any> {
        activity?.toast(it.toString())
    }

    private fun getDepartment(): (Department) -> Unit {
        return { goToDetail(it) }
    }

    private fun goToDetail(it: Department) {
        listener.goToDetail(it)
    }

    private fun handleGarantedPermission(granted:Boolean){
        if (granted) viewmodel
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is HomeFragmentListener) listener = context
    }

    companion object {
        val TAG = HomeFragment::class.java.name
        fun newInstance() = HomeFragment()
    }

    interface HomeFragmentListener {
        fun goToDetail(it: Department)
    }
}
