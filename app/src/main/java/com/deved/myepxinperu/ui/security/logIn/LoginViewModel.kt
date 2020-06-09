package com.deved.myepxinperu.ui.security.logIn

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.deved.data.common.DataResponse
import com.deved.domain.User
import com.deved.interactors.LogIn
import com.deved.myepxinperu.R
import com.deved.myepxinperu.coroutines.ScopeViewModel
import com.deved.myepxinperu.ui.common.UiContext
import com.deved.myepxinperu.ui.common.validate
import kotlinx.coroutines.launch

class LoginViewModel(private val useCase: LogIn) : ScopeViewModel() {
    private var _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> get() = _isViewLoading
    private var _onMessageError = MutableLiveData<Any>()
    val onMessageError: LiveData<Any> get() = _onMessageError
    private var _onMessageSuccess = MutableLiveData<User>()
    val onMessageSuccess: LiveData<User> get() = _onMessageSuccess

    fun validateForLogIn(email: String, password: String) {
        if (!email.validate()) _onMessageError.postValue(UiContext.getString(R.string.invalidInputEmail))
        else if (!password.validate()) _onMessageError.postValue(UiContext.getString(R.string.invalidInputPassword))
        else logIn(email, password)
    }

    private fun logIn(email: String, password: String) {
        launch {
            _isViewLoading.postValue(true)
            doAction(useCase.invoke(email, password))
            _isViewLoading.postValue(false)
        }
    }

    private fun doAction(invoke: DataResponse<User>) {
        when (invoke) {
            is DataResponse.Success -> _onMessageSuccess.postValue(invoke.data)
            is DataResponse.NetworkError -> _onMessageError.postValue(invoke.error)
            is DataResponse.TimeOutServerError -> _onMessageError.postValue(invoke.error)
            is DataResponse.ExceptionError -> _onMessageError.postValue(invoke.errorCode.message)
            is DataResponse.ServerError -> _onMessageError.postValue(invoke.errorCode)
        }
    }
}