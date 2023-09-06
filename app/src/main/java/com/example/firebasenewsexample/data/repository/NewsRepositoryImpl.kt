package com.example.firebasenewsexample.data.repository

import android.net.Uri
import com.example.firebasenewsexample.Constants
import com.example.firebasenewsexample.Constants.EDITOR_ID
import com.example.firebasenewsexample.Constants.NEWS
import com.example.firebasenewsexample.data.model.News
import com.example.firebasenewsexample.data.model.User
import com.example.firebasenewsexample.data.state.AddNewsPhotoState
import com.example.firebasenewsexample.data.state.AllNewsState
import com.example.firebasenewsexample.data.state.ProfilePhotoUpdateState
import com.example.firebasenewsexample.data.state.UserListState
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import okhttp3.Dispatcher
import java.util.UUID
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseStorage: FirebaseStorage,

): NewsRepository{
    override suspend fun addNews(news: News, uris:List<Uri>) {
        firestore.collection(NEWS).add(news).addOnSuccessListener {
            it.update(mapOf("id" to it.id))

            //upload photo
            //update news

            CoroutineScope(Dispatchers.IO).launch {
                uploadNewsPhoto(it.id,uris)
            }
        }
    }

    private fun uploadNewsPhoto(newsId: String, uris: List<Uri>) {
        uris.forEach {
            val photoId = UUID.randomUUID().toString()
            firebaseStorage.reference.child(photoId).putFile(it).addOnCompleteListener{
                if(it.isSuccessful){
                    CoroutineScope(Dispatchers.IO).launch {
                        val downloadUrl = it.result.storage.downloadUrl.await()
                        firestore.collection(NEWS).document(newsId).update("photos", FieldValue.arrayUnion(downloadUrl))
                    }
                }
            }
        }

    }

    override suspend fun allNews(editorId: String?): List<News> {
        return editorId?.let {
            firestore.collection(NEWS).whereEqualTo(EDITOR_ID,it).get().await().toObjects(News::class.java)
        }?: kotlin.run {
            firestore.collection(NEWS).get().await().toObjects(News::class.java)
        }
    }

}