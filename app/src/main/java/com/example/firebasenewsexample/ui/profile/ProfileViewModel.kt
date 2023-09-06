package com.example.firebasenewsexample.ui.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasenewsexample.Constants.NAME
import com.example.firebasenewsexample.Constants.SURNAME
import com.example.firebasenewsexample.data.model.User
import com.example.firebasenewsexample.data.repository.UserRepository
import com.example.firebasenewsexample.data.state.ProfilePhotoUpdateState
import com.example.firebasenewsexample.data.state.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val _profilePhotoUpdateState:MutableSharedFlow<ProfilePhotoUpdateState>,
):ViewModel() {

    val profilePhotoUpdateState = _profilePhotoUpdateState.asSharedFlow()

    private val _profileState:MutableStateFlow<ProfileState> = MutableStateFlow(ProfileState.Idle)
    val profileState:StateFlow<ProfileState> = _profileState

    private val _signedUser:MutableStateFlow<User?> = MutableStateFlow(null)
    val signedUser:StateFlow<User?> = _signedUser

    fun showProfile(){
        viewModelScope.launch {
            _profileState.value = ProfileState.Loading
            kotlin.runCatching {
                _profileState.value = ProfileState.Result(userRepository.getSignedUser())
            }.onFailure {
                _profileState.value = ProfileState.Error
            }
        }
    }

    fun getUser(){
        viewModelScope.launch {
            _signedUser.emit(userRepository.getSignedUser())
        }
    }

    fun uploadProfileImage(uri: Uri){
        viewModelScope.launch {
            userRepository.uploadProfileImage(uri)
        }
    }
    fun updateProfile(name:String, surname:String){
        viewModelScope.launch {

            val map = mapOf(
                NAME to name,
                SURNAME to surname
            )
            val state = userRepository.updateProfile(map)
        }
    }

}