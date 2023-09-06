package com.example.firebasenewsexample.data.model

import com.google.firebase.Timestamp


data class User(
    val id: String? = null,
    val email: String? = null,
    val name: String? = null,
    val surName: String? = null,
    val profileImageUrl: String? = null,
    val registerTimestamp: Timestamp = Timestamp.now(),
)

fun User.getFullNameOrEmail() =
    if (!name.isNullOrEmpty() && !surName.isNullOrEmpty()) "$name $surName" else email
