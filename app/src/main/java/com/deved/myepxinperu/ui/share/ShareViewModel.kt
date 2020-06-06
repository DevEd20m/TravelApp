package com.deved.myepxinperu.ui.share

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.deved.data.common.DataResponse
import com.deved.interactors.GetPicture
import com.deved.interactors.RegisterExp
import com.deved.interactors.UploadPicture
import com.deved.myepxinperu.R
import com.deved.myepxinperu.coroutines.ScopeViewModel
import com.deved.myepxinperu.ui.common.UiContext
import com.deved.myepxinperu.ui.common.validate
import com.deved.myepxinperu.ui.mapper.DepartmentMapper
import com.deved.myepxinperu.ui.model.DepartmentView
import com.deved.myepxinperu.ui.model.PlacesView
import kotlinx.coroutines.Dispatchers
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
        name: String, description: String,
        pictureOne: String, pictureSecond: String
    ) {
        if (!name.validate()) _onMessageError.postValue(UiContext.getString(R.string.invalidInputName))
        if (!description.validate()) _onMessageError.postValue(UiContext.getString(R.string.invalidInputDescription))
        else if (!pictureOne.validate()) _onMessageError.postValue(UiContext.getString(R.string.invalidInputPictureOne))
        else if (!pictureSecond.validate()) _onMessageError.postValue(UiContext.getString(R.string.invalidInputPictureSecond))
        else shareExp(name,description, pictureOne, pictureSecond ,"")
    }

    fun getPicture() {
        launch {
            useCasePicture.invoke()
        }
    }

    private fun shareExp(
        name:String,
        description: String,
        one: String,
        second: String,
        createAt:String
    ) {
        launch {
            val listPicture = mutableListOf<PlacesView>()
            _isViewLoading.postValue(true)
            val one = withContext(Dispatchers.IO) { uploadPicture.invoke(one) }
            val second = withContext(Dispatchers.IO) { uploadPicture.invoke(second) }
            val resultFirst = uploadDoAction(one).toString()
            val resultSecond = uploadDoAction(second).toString()
            if(resultFirst.startsWith("https")){
                listPicture.add(PlacesView(resultFirst))
            }else if(resultSecond.startsWith("https")){
                listPicture.add(PlacesView(resultSecond))
            }
            doAction(useCase.invoke(DepartmentMapper().mapToEntity(DepartmentView(name,description,listPicture,createAt))))
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

    private fun uploadDoAction(invoke: DataResponse<String>): Any? {
        return when (invoke) {
            is DataResponse.Success -> invoke.data
            is DataResponse.NetworkError -> _onMessageError.postValue(invoke.error)
            is DataResponse.TimeOutServerError -> _onMessageError.postValue(invoke.error)
            is DataResponse.ExceptionError -> _onMessageError.postValue(invoke.errorCode.message)
            is DataResponse.ServerError -> _onMessageError.postValue(invoke.errorCode)
        }
    }
}