package com.example.firebasenewsexample.ui.addnews

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasenewsexample.data.model.News
import com.example.firebasenewsexample.data.repository.NewsRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val firebaseAuth: FirebaseAuth
) :ViewModel(){

    private val _newsPhotos:MutableStateFlow<List<Uri>> = MutableStateFlow(emptyList())
    //val newsPhotos:SharedFlow<List<Uri>> = _newsPhotos

    fun addNews(title:String,content:String){
        viewModelScope.launch {
            val news = News(title = title, content = content, userId = firebaseAuth.currentUser?.uid)
            newsRepository.addNews(news,_newsPhotos.first())
        }
    }

    fun setNewsPhotos(uris: List<Uri>) {
        viewModelScope.launch {
            _newsPhotos.emit(uris)
        }
    }
}