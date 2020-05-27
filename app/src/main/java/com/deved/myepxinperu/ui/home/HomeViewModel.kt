package com.deved.myepxinperu.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.deved.data.common.DataResponse
import com.deved.domain.Places
import com.deved.interactors.GetAllPlaces
import com.deved.myepxinperu.coroutines.ScopeViewModel
import kotlinx.coroutines.launch

class HomeViewModel(
    private val fetchPlaces: GetAllPlaces
) : ScopeViewModel() {
    private var _places = MutableLiveData<List<Places>>().apply { value = null }
    val places: LiveData<List<Places>> get() = _places
    private var _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> get() = _isViewLoading
    private var _onMessageError = MutableLiveData<Any>()
    val onMessageError: LiveData<Any> get() = _onMessageError

    init {
        fetchPlaces()
    }

    private fun fetchPlaces() = launch {
        _isViewLoading.postValue(true)
        doActionResult(fetchPlaces.invoke())
        _isViewLoading.postValue(false)
    }

    private fun doActionResult(invoke: DataResponse<List<Places>>) {
        when (invoke) {
            is DataResponse.Success -> _places.value = invoke.data
            is DataResponse.NetworkError -> _onMessageError.postValue(invoke.error)
            is DataResponse.TimeOutServerError -> _onMessageError.postValue(invoke.error)
            is DataResponse.ExceptionError -> _onMessageError.postValue(invoke.errorCode)
        }
    }
}