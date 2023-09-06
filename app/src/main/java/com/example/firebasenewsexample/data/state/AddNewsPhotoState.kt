package com.example.firebasenewsexample.data.state

sealed class AddNewsPhotoState{
    object Idle:AddNewsPhotoState()
    object Loading:AddNewsPhotoState()
    class Progress(val progress:Int):AddNewsPhotoState()
    object Success:AddNewsPhotoState()
    class Error(val throwable: Throwable): AddNewsPhotoState()
}
