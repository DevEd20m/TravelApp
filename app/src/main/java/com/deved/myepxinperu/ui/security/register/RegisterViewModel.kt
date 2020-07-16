package com.deved.myepxinperu.ui.security.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.deved.data.common.DataResponse
import com.deved.domain.User
import com.deved.interactors.RegisterUser
import com.deved.myepxinperu.R
import com.deved.myepxinperu.coroutines.ScopeViewModel
import com.deved.myepxinperu.ui.common.UiContext
import com.deved.myepxinperu.ui.common.validate
import kotlinx.coroutines.launch

class RegisterViewModel(private val registerUser: RegisterUser) : ScopeViewModel() {
    private var _onMessageSucces = MutableLiveData<Any>()
    val onMessageSucces: LiveData<Any> get() = _onMessageSucces
    private var _onMessageError = MutableLiveData<Any>()
    val onMessageError: LiveData<Any> get() = _onMessageError
    private var _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> get() = _isViewLoading

    fun validateForRegister(name: String, lastName: String, email: String, password: String) {
        if (!name.validate()) _onMessageError.postValue(UiContext.getString(R.string.invalidInputName))
        else if (!lastName.validate()) _onMessageError.postValue(UiContext.getString(R.string.invalidInputLastName))
        else if (!email.validate()) _onMessageError.postValue(UiContext.getString(R.string.invalidInputEmail))
        else if (!password.validate()) _onMessageError.postValue(UiContext.getString(R.string.invalidInputPassword))
        else registerUser(User(null, name, lastName, email, password, null))
    }

    private fun registerUser(user: User) {
        launch {
            _isViewLoading.postValue(true)
            doAction(registerUser.invoke(user))
            _isViewLoading.postValue(false)
        }
    }

    private fun doAction(invoke: DataResponse<String>) {
        when (invoke) {
            is DataResponse.Success -> _onMessageSucces.postValue(invoke.data)
            is DataResponse.NetworkError -> _onMessageError.postValue(invoke.error)
            is DataResponse.TimeOutServerError -> _onMessageError.postValue(invoke.error)
            is DataResponse.ExceptionError -> _onMessageError.postValue(invoke.errorCode)
            is DataResponse.ServerError -> _onMessageError.postValue(invoke.errorCode)
        }
    }
}