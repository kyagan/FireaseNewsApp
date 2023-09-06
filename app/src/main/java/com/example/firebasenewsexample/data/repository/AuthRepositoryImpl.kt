package com.example.firebasenewsexample.data.repository


import com.example.firebasenewsexample.data.state.LoginState
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(private val firebaseAuth: FirebaseAuth) :
    AuthRepository {

    override suspend fun login(email: String, password: String): LoginState {
        val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
        return if (authResult.user == null) LoginState.UserNotFound else LoginState.Success
    }

    override suspend fun register(email: String, password: String): AuthResult {
        return firebaseAuth.createUserWithEmailAndPassword(email, password).await()
    }

}