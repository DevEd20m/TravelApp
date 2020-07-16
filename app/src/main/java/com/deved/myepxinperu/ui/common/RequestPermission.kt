package com.deved.myepxinperu.ui.common

sealed class RequestPermission{
    object RequestStorage:RequestPermission()
    object RequestLocation:RequestPermission()
}