package com.example.firebasenewsexample.data.state

sealed class ProfilePhotoUpdateState{
    object Idle:ProfilePhotoUpdateState()
    object Loading:ProfilePhotoUpdateState()
    class Progress(val progress:Int):ProfilePhotoUpdateState()
    object Success:ProfilePhotoUpdateState()
    class Error(val throwable: Throwable): ProfilePhotoUpdateState()
}
