package com.deved.myepxinperu.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.deved.data.common.DataResponse
import com.deved.data.repository.PermissionsChecker
import com.deved.domain.Department
import com.deved.interactors.GetAllDepartment
import com.deved.myepxinperu.coroutines.ScopeViewModel
import com.deved.myepxinperu.ui.common.RequestPermission
import kotlinx.coroutines.launch

class HomeViewModel(
    private val fetchDepartment: GetAllDepartment,
    private val permissionChecker: PermissionsChecker
) : ScopeViewModel() {
    private var _places = MutableLiveData<List<Department>>().apply { value = null }
    val places: LiveData<List<Department>> get() = _places
    private var _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> get() = _isViewLoading
    private var _onMessageError = MutableLiveData<Any>()
    val onMessageError: LiveData<Any> get() = _onMessageError
    private var _isPermissionGaranted = MutableLiveData<RequestPermission>()
    val isPermissionGaranted: LiveData<RequestPermission> get() = _isPermissionGaranted

    fun fetchDepartment() = launch {
        _isViewLoading.postValue(true)
        doActionResult(fetchDepartment.invoke())
        _isViewLoading.postValue(false)
    }

    private fun doActionResult(invoke: DataResponse<List<Department>>) {
        when (invoke) {
            is DataResponse.Success -> _places.value = invoke.data
            is DataResponse.NetworkError -> _onMessageError.postValue(invoke.error)
            is DataResponse.TimeOutServerError -> _onMessageError.postValue(invoke.error)
            is DataResponse.ExceptionError -> _onMessageError.postValue(invoke.errorCode)
        }
    }

    fun requestPermission() {
        if (!permissionChecker.checkPermission(PermissionsChecker.Permissions.COARSE_LOCATION)
        ) {
            _isPermissionGaranted.value = RequestPermission.RequestLocation
        }
    }
}