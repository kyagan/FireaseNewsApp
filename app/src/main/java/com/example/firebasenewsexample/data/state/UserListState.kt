package com.example.firebasenewsexample.data.state

import com.example.firebasenewsexample.data.model.User

sealed class UserListState{
    object Idle:UserListState()
    object Loading:UserListState()
    object Empty:UserListState()
    class Result(val users:List<User>):UserListState()
    class Error(val throwable: Throwable?=null):UserListState()

}
