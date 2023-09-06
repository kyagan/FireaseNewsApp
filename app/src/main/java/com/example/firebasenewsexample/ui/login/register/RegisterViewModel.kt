package com.example.firebasenewsexample.ui.login.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasenewsexample.data.model.User
import com.example.firebasenewsexample.data.repository.AuthRepository
import com.example.firebasenewsexample.data.repository.UserRepository
import com.example.firebasenewsexample.data.state.RegisterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _registerState: MutableSharedFlow<RegisterState> = MutableSharedFlow()
    val registerState: SharedFlow<RegisterState> = _registerState

    private val _message: MutableSharedFlow<String> = MutableSharedFlow()
    val message: SharedFlow<String> = _message

    fun register(email: String, pass: String, passAgain: String) {

        viewModelScope.launch {
            runCatching {
                if (email.isNotEmpty() && pass.isNotEmpty() && passAgain.isNotEmpty()) {
                    if (pass == passAgain) {
                        _registerState.emit(RegisterState.Loading)
                        authRepository.register(email, pass).user?.let {
                            val user = User(it.uid, it.email)
                            userRepository.insert(user)
                        }
                        _registerState.emit(RegisterState.Success)
                    } else {
                        _message.emit("Pass are not matching")
                    }
                } else {
                    _message.emit("Do not leave fields empty")
                }
            }.onFailure {
                _registerState.emit(RegisterState.Error(it))
            }
        }
    }

}