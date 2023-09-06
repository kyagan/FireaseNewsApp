package com.example.firebasenewsexample.data.state

sealed class RegisterState {
    data object Idle : RegisterState()
    data object Success : RegisterState()
    data object Loading : RegisterState()
    class Error(val throwable: Throwable? = null) : RegisterState()
}