package com.example.firebasenewsexample.data.repository

import android.net.Uri
import com.example.firebasenewsexample.data.model.News
import com.example.firebasenewsexample.data.state.AllNewsState

interface NewsRepository {

    suspend fun addNews(news: News, uris:List<Uri>)
    suspend fun allNews(editorId:String?) : List<News>
}