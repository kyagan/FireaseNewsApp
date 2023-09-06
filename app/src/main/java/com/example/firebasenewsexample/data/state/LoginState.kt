package com.example.firebasenewsexample.data.state

sealed class LoginState{
    object Idle:LoginState()
    object Loading:LoginState()
    object UserNotFound:LoginState()
    object Success:LoginState()
    data class Error(val throwable: Throwable?= null):LoginState()
}
