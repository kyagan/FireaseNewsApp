package com.example.firebasenewsexample.data.state

import com.example.firebasenewsexample.data.model.News

sealed class AllNewsState{
    object Idle:AllNewsState()
    object Loading:AllNewsState()
    object Empty:AllNewsState()
    class Result(val news: List<News>) : AllNewsState()
    class Error(val throwable: Throwable):AllNewsState()
}
