package com.deved.myepxinperu.ui.security.register

import com.deved.domain.User
import com.deved.interactors.RegisterUser
import com.deved.myepxinperu.coroutines.ScopeViewModel

class RegisterViewModel(private val registerUser: RegisterUser) :ScopeViewModel(){

    suspend fun registerUser(user: User){
        registerUser.invoke(user)
    }
}