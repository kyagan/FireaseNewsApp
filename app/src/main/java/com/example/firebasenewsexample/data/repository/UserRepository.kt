package com.example.firebasenewsexample.data.repository

import android.net.Uri
import com.example.firebasenewsexample.data.model.User
import com.example.firebasenewsexample.data.state.UserListState

interface UserRepository {

    suspend fun insert(user: User)
    suspend fun getSignedUser(): User?
    suspend fun getAllUsers(): UserListState
    suspend fun sendPasswordResetEmail(emailAddress:String)

    suspend fun uploadProfileImage(uri: Uri)

    suspend fun updateProfile(map:Map<String ,String>)


}