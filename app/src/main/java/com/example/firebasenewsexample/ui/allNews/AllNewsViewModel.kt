package com.example.firebasenewsexample.ui.allNews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasenewsexample.data.repository.NewsRepository
import com.example.firebasenewsexample.data.state.AllNewsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AllNewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
):ViewModel() {

    private val _allNewsState:MutableStateFlow<AllNewsState> = MutableStateFlow(AllNewsState.Idle)
    val allNewsState:StateFlow<AllNewsState> = _allNewsState


    fun getAllNews(editorId: String?){
        viewModelScope.launch {
            kotlin.runCatching {
                _allNewsState.value = AllNewsState.Loading
                _allNewsState.value = AllNewsState.Result(newsRepository.allNews(editorId))
            }.onFailure {
                _allNewsState.value = AllNewsState.Error(it)
            }
        }
    }

}