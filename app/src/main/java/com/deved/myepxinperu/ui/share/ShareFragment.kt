package com.deved.myepxinperu.ui.share

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.deved.myepxinperu.databinding.FragmentShareBinding
import com.deved.myepxinperu.ui.common.PermissionRequester
import com.deved.myepxinperu.ui.common.RequestPermission
import com.deved.myepxinperu.ui.common.UserSingleton
import com.deved.myepxinperu.ui.common.toast
import com.deved.myepxinperu.ui.model.Picture
import org.koin.android.scope.lifecycleScope
import org.koin.android.viewmodel.scope.viewModel

class ShareFragment : Fragment() {
    private lateinit var binding: FragmentShareBinding
    private val viewModel: ShareViewModel by lifecycleScope.viewModel(this)
    private lateinit var picturesArray: MutableList<Picture>
    private val adapter by lazy { ShareAdapter(getListener()) }
    private lateinit var requestManager: PermissionRequester

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShareBinding.inflate(inflater, container, false)
        setUpViewModelObserver()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestManager = PermissionRequester(requireActivity())
        setUpRecyclerView()
        setUpEvents()
    }

    private fun setUpRecyclerView() {
        binding.recyclerViewPictures.layoutManager = GridLayoutManager(activity, 2)
        binding.recyclerViewPictures.adapter = adapter
        picturesArray = mutableListOf()
    }

    private fun setUpViewModelObserver() {
        viewModel.isViewLoading.observe(viewLifecycleOwner, isViewLoadingObserver)
        viewModel.onMessageError.observe(viewLifecycleOwner, onMessageErrorObserver)
        viewModel.onMessageSuccess.observe(viewLifecycleOwner, onMessageSuccessObserver)
        viewModel.permission.observe(viewLifecycleOwner, permissionObserver)
        viewModel.takePicture.observe(viewLifecycleOwner, takePictureObserver)
    }

    private fun setUpEvents() {
        binding.materialButtonShareExp.setOnClickListener {
            val department = binding.textInputEditDepartment.text?.trim().toString()
            val touristName = binding.textInputEditTextNameTourist.text?.trim().toString()
            val touristDescription =
                binding.textInputEditTextTouristDescription.text?.trim().toString()

            val one = picturesArray[0]
            val second = picturesArray[1]
            val userId = UserSingleton.getUid()
            viewModel.validateRegisterExp(
                department,
                touristName,
                touristDescription,
                one.picture.toString(),
                second.picture.toString(),
                userId
            )
        }

        binding.imageButtonAdd.setOnClickListener {
            viewModel.getPicture()
        }
    }

    private fun getListener(): (Picture) -> Unit {
        return {
            deletePicture(it)
        }
    }

    private fun deletePicture(item: Picture) {
        picturesArray.remove(item)
        adapter.setData(picturesArray)
    }

    private val isViewLoadingObserver = Observer<Boolean> {
        binding.progressBarShareExp.isVisible = it
    }

    private val onMessageErrorObserver = Observer<Any> {
        activity?.toast(it.toString())
    }

    private val onMessageSuccessObserver = Observer<Any> {
        activity?.toast(it.toString())
    }

    private val takePictureObserver = Observer<Boolean> {
        fetchPicture()
    }

    private val permissionObserver = Observer<RequestPermission> {
        when (it) {
            is RequestPermission.RequestStorage -> {
                requestManager.request(READ_EXTERNAL_STORAGE, ::handleGarantedPermission)
                requestManager.request(WRITE_EXTERNAL_STORAGE, ::handleGarantedPermission)
            }
        }
    }

    private fun handleGarantedPermission(granted: Boolean) {
        if (granted) viewModel.getPicture()
    }

    private fun fetchPicture() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 1) {
            picturesArray.add(Picture(data?.data, "Imagen2", "Descripción2"))
            adapter.setData(picturesArray)
        }
    }

    companion object {
        val TAG = ShareFragment::class.java.name
        fun newInstance() = ShareFragment()
    }
}
