package com.deved.myepxinperu.ui.share

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.deved.data.common.DataResponse
import com.deved.domain.Places
import com.deved.interactors.GetPicture
import com.deved.interactors.RegisterExp
import com.deved.interactors.UploadPicture
import com.deved.myepxinperu.R
import com.deved.myepxinperu.coroutines.ScopeViewModel
import com.deved.myepxinperu.ui.common.UiContext
import com.deved.myepxinperu.ui.common.validate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShareViewModel(
    private val useCase: RegisterExp,
    private val useCasePicture: GetPicture,
    private val uploadPicture: UploadPicture
) : ScopeViewModel() {
    private var _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> get() = _isViewLoading
    private var _onMessageError = MutableLiveData<Any>()
    val onMessageError: LiveData<Any> get() = _onMessageError
    private var _onMessageSuccess = MutableLiveData<Any>()
    val onMessageSuccess: LiveData<Any> get() = _onMessageSuccess

    fun validateRegisterExp(
        description: String, pictureOne: String, pictureSecond: String, department: String
    ) {
        if (!description.validate()) _onMessageError.postValue(UiContext.getString(R.string.invalidInputDescription))
        else if (!pictureOne.validate()) _onMessageError.postValue(UiContext.getString(R.string.invalidInputPictureOne))
        else if (!pictureSecond.validate()) _onMessageError.postValue(UiContext.getString(R.string.invalidInputPictureSecond))
        else if (!department.validate()) _onMessageError.postValue(UiContext.getString(R.string.invalidInputDeparment))
        else shareExp(description, pictureOne, pictureSecond, "", department)
    }

    fun getPicture() {
        launch {
            useCasePicture.invoke()
        }
    }

    private fun shareExp(description: String,one:String,second:String,createAt:String,department:String) {
        launch {
            _isViewLoading.postValue(true)
            val one = withContext(Dispatchers.IO){uploadPicture.invoke(one)}
            val second  =  withContext(Dispatchers.IO){uploadPicture.invoke(second)}
            val resultOne = uploadDoAction(one).toString()
            val resultSecond = uploadDoAction(second).toString()
            if(resultOne.startsWith("https") && resultSecond.startsWith("https")){
                doAction(useCase.invoke(Places(description,description,resultOne, resultSecond,createAt,department)))
            }
            _isViewLoading.postValue(false)
        }
    }

    private fun doAction(invoke: DataResponse<String>) {
        when (invoke) {
            is DataResponse.Success -> _onMessageSuccess.postValue(UiContext.getString(R.string.success_registered_shared))
            is DataResponse.NetworkError -> _onMessageError.postValue(invoke.error)
            is DataResponse.TimeOutServerError -> _onMessageError.postValue(invoke.error)
            is DataResponse.ExceptionError -> _onMessageError.postValue(invoke.errorCode.message)
            is DataResponse.ServerError -> _onMessageError.postValue(invoke.errorCode)
        }
    }

    private fun uploadDoAction(invoke: DataResponse<String>):Any? {
        return when (invoke) {
            is DataResponse.Success -> invoke.data
            is DataResponse.NetworkError -> _onMessageError.postValue(invoke.error)
            is DataResponse.TimeOutServerError -> _onMessageError.postValue(invoke.error)
            is DataResponse.ExceptionError -> _onMessageError.postValue(invoke.errorCode.message)
            is DataResponse.ServerError -> _onMessageError.postValue(invoke.errorCode)
        }
    }
}