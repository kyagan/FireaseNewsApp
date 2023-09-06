package com.example.firebasenewsexample.data.state

import com.example.firebasenewsexample.data.model.User

sealed class ProfileState {
    object Idle:ProfileState()
    object Loading:ProfileState()
    class Result(val user: User?) : ProfileState()
    object Error:ProfileState()

}