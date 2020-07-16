package com.deved.myepxinperu.coroutines

import androidx.lifecycle.ViewModel

abstract class ScopeViewModel : ViewModel(), Scope by Scope.Impl(){
    init {
        this.initScope()
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }

}