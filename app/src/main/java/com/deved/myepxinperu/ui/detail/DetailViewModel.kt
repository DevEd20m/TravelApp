package com.deved.myepxinperu.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.deved.data.common.DataResponse
import com.deved.domain.Places
import com.deved.interactors.GetDetailPlace
import com.deved.myepxinperu.coroutines.ScopeViewModel
import kotlinx.coroutines.launch

class DetailViewModel(private val useCase:GetDetailPlace) : ScopeViewModel() {
    private  var _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> get() = _isViewLoading
    private  var _onErrorMessage = MutableLiveData<Any>()
    val onErrorMessage: LiveData<Any> get() = _onErrorMessage
    private  var _onSuccessMessage = MutableLiveData<Any>()
    val onSuccessMessage: LiveData<Any> get() = _onSuccessMessage
    private  var _place = MutableLiveData<Places>()
    val place: LiveData<Places> get() = _place

    init {
        getDetailPlace(1)
    }
    private fun getDetailPlace(touristId:Int){
        launch {
            _isViewLoading.postValue(true)
            doActionGetDetail(useCase.invoke(touristId))
            _isViewLoading.postValue(false)
        }
    }

    private fun doActionGetDetail(invoke: DataResponse<Places>) {
        when(invoke){
            is DataResponse.Success -> _onSuccessMessage.postValue(invoke.data)
            is DataResponse.NetworkError -> _onErrorMessage.postValue(invoke.error)
            is DataResponse.TimeOutServerError -> _onErrorMessage.postValue(invoke.error)
            is DataResponse.ExceptionError -> _onErrorMessage.postValue(invoke.errorCode.message)
            is DataResponse.ServerError -> _onErrorMessage.postValue(invoke.errorCode)
        }
    }
}