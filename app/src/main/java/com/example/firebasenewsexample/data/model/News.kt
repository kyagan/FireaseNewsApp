package com.example.firebasenewsexample.data.model

import java.sql.Timestamp


data class News(
    val id:String?= null,
    val title: String?= null,
    val content:String?= null,
    val userId:String? =null,
    val createdAt:com.google.firebase.Timestamp = com.google.firebase.Timestamp.now(),
    val updatedAt:com.google.firebase.Timestamp?=null,
    val photos:List<String>?=null,
)
