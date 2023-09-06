package com.example.firebasenewsexample.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasenewsexample.data.model.User
import com.example.firebasenewsexample.data.repository.UserRepository
import com.example.firebasenewsexample.data.state.UserListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashBoardViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {

    private val _user: MutableSharedFlow<User> = MutableSharedFlow()
    val user: SharedFlow<User> = _user

    private val _userListState:MutableStateFlow<UserListState> = MutableStateFlow(UserListState.Idle)
    val userListState: StateFlow<UserListState> = _userListState

    init {
        viewModelScope.launch {
            userRepository.getSignedUser()?.let {
                _user.emit(it)
            }?: run {
            }
        }
    }


    fun getUsers(){
        viewModelScope.launch {
            kotlin.runCatching {
                _userListState.value = UserListState.Loading
                _userListState.value = userRepository.getAllUsers()
            }.onFailure {
                _userListState.value = UserListState.Error(it)
            }
        }
    }
}