package com.example.firebasenewsexample.data.repository

import android.net.Uri
import com.example.firebasenewsexample.Constants
import com.example.firebasenewsexample.Constants.PROFILE_IMAGE_URL
import com.example.firebasenewsexample.Constants.USERS
import com.example.firebasenewsexample.data.model.User
import com.example.firebasenewsexample.data.state.ProfilePhotoUpdateState
import com.example.firebasenewsexample.data.state.UserListState
import com.example.firebasenewsexample.ui.profile.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseStorage: FirebaseStorage,
    private val firebaseAuth: FirebaseAuth,
    private val profilePhotoUpdateState: MutableSharedFlow<ProfilePhotoUpdateState>
) :
    UserRepository {
    override suspend fun insert(user: User) {
        firestore.collection(Constants.USERS).document(user.id.toString()).set(user)
    }

    override suspend fun getSignedUser(): User? {
       return firestore.collection(Constants.USERS).document(firebaseAuth.currentUser?.uid.orEmpty()).get().await().toObject(User::class.java)
    }

    override suspend fun getAllUsers(): UserListState{
        val querySnapshot = firestore.collection(USERS).get().await()
        return if (querySnapshot.isEmpty) UserListState.Empty
        else UserListState.Result(querySnapshot.toObjects(User::class.java))
    }

    override suspend fun sendPasswordResetEmail(emailAddress: String) {
        firebaseAuth.sendPasswordResetEmail(emailAddress).await()
    }

    override suspend fun uploadProfileImage(uri: Uri) {
        val reference = firebaseStorage.reference.child(firebaseAuth.currentUser?.uid.toString())
        val uploadTask = reference.putFile(uri)
        profilePhotoUpdateState.emit(ProfilePhotoUpdateState.Loading)
        uploadTask.addOnCompleteListener{task->
            if (task.isSuccessful){
                CoroutineScope(Dispatchers.IO).launch {
                    val downloadUri = task.result.storage.downloadUrl.await()
                    updateUserProfileImage(downloadUri.toString())
                }
            }else{

            }

        }.addOnProgressListener { taskSnapshot->
            val pr = (100.0 * taskSnapshot.bytesTransferred) / taskSnapshot.totalByteCount
            CoroutineScope(Dispatchers.IO).launch { profilePhotoUpdateState.emit(ProfilePhotoUpdateState.Progress(pr.toInt())) }
        }.addOnSuccessListener {
            CoroutineScope(Dispatchers.IO).launch { profilePhotoUpdateState.emit(ProfilePhotoUpdateState.Success) }
        }.addOnFailureListener{
            CoroutineScope(Dispatchers.IO).launch { profilePhotoUpdateState.emit(ProfilePhotoUpdateState.Error(it)) }
        }
    }

    private suspend fun updateUserProfileImage(downloadPath: String) {
        firestore.collection(USERS).document(firebaseAuth.currentUser?.uid.toString()).update(mapOf( PROFILE_IMAGE_URL to downloadPath)).await()
    }

    override suspend fun updateProfile(map: Map<String, String>) {
        firestore.collection(USERS).document(firebaseAuth.currentUser?.uid.toString()).update(map).await()
    }


}