package com.example.firebasenewsexample.data.state

sealed class ResetPasswordState{

    object Idle:ResetPasswordState()
    object Loading:ResetPasswordState()
    object Success:ResetPasswordState()
    class Error(val throwable: Throwable):ResetPasswordState()
}
