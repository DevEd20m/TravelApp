package com.deved.myepxinperu.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.deved.data.repository.LocationRepository
import com.deved.data.repository.PlacesRepository
import com.deved.domain.Department
import com.deved.interactors.GetAllDepartment
import com.deved.interactors.RequestPermission
import com.deved.myepxinperu.data.AndroidPermissionsChecker
import com.deved.myepxinperu.data.dataBase.PlaceDataBase
import com.deved.myepxinperu.data.dataBase.RoomUserDataSource
import com.deved.myepxinperu.data.device.PlayServicesLocationDataSource
import com.deved.myepxinperu.data.server.FirebasePlacesDataSource
import com.deved.myepxinperu.databinding.FragmentHomeBinding
import com.deved.myepxinperu.ui.common.getViewModel
import com.deved.myepxinperu.ui.common.toast
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment() {
    private lateinit var firebaseFirestore: FirebaseFirestore
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewmodel: HomeViewModel
    private val adapter by lazy { HomeAdapter(getDepartment()) }
    private lateinit var listener: HomeFragmentListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        setUpViewModel()
        viewmodel.fetchDepartment()
        attachViewModelObservers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewPlaces.adapter = adapter
        viewmodel.requestPermission()
    }

    private fun setUpViewModel() {
        viewmodel = getViewModel {
            firebaseFirestore = FirebaseFirestore.getInstance()
            val useCase =
                GetAllDepartment(PlacesRepository(FirebasePlacesDataSource(firebaseFirestore)))
            val dataSource = RoomUserDataSource(PlaceDataBase.getDatabaseInstance(requireContext()))
            val playServices = PlayServicesLocationDataSource(requireContext())
            val permission = AndroidPermissionsChecker(activity)
            val requestPermission = RequestPermission(LocationRepository(dataSource,playServices,permission))
            HomeViewModel(useCase,requestPermission)
        }
    }

    private fun attachViewModelObservers() {
        viewmodel.places.observe(viewLifecycleOwner, placesObserver)
        viewmodel.isViewLoading.observe(viewLifecycleOwner, isViewLoadingObserver)
        viewmodel.onMessageError.observe(viewLifecycleOwner, onMessageErrorObserver)
        viewmodel.isPermissionGaranted.observe(viewLifecycleOwner, isPermissionGarantedObserver)
    }

    private val placesObserver = Observer<List<Department>> {
        it?.let { places ->
            adapter.places = it
        }
    }

    private val isViewLoadingObserver = Observer<Boolean> {
        binding.progressBarHome.isVisible = it
    }

    private val isPermissionGarantedObserver = Observer<Boolean> {
        if(!it){
            viewmodel.requestPermission()
        }else{
            viewmodel.validatePermissions()
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is HomeFragmentListener) listener = context
    }
    companion object {
        val TAG = HomeFragment::class.java.name
        fun newInstance() = HomeFragment()
    }

    interface HomeFragmentListener{
        fun goToDetail(it: Department)
    }
}
