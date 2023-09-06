package com.example.firebasenewsexample.ui.forgot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasenewsexample.data.repository.UserRepository
import com.example.firebasenewsexample.data.state.ResetPasswordState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val userRepository: UserRepository
):ViewModel(){

    private val _resetPasswordState:MutableStateFlow<ResetPasswordState> = MutableStateFlow(ResetPasswordState.Idle)
    val resetPasswordState:StateFlow<ResetPasswordState> = _resetPasswordState

    fun sendResetPasswordLink(emailAddress:String){
        viewModelScope.launch {
            kotlin.runCatching {
                _resetPasswordState.value = ResetPasswordState.Loading
                userRepository.sendPasswordResetEmail(emailAddress)
                _resetPasswordState.value = ResetPasswordState.Success
            }.onFailure {
                _resetPasswordState.value = ResetPasswordState.Error(it)
            }
        }
    }


}