package com.deved.myepxinperu.coroutines

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel

abstract class ScopeViewModel : ViewModel(), Scope by Scope.Impl(){
    init {
        initScope()
    }

    @CallSuper
    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }

}