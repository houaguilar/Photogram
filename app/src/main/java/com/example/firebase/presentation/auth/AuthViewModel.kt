package com.example.firebase.presentation.auth

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.firebase.core.Resource
import com.example.firebase.domain.auth.AuthRepo
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class AuthViewModel(private val repo: AuthRepo): ViewModel() {

    fun signIn(email: String, password: String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(repo.signIn(email, password)))
        } catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }

    fun signUp(email: String, password: String, username: String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(repo.signUp(email, password, username)))
        } catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }

    fun updateUserProfile(imageBitmap: Bitmap, username: String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(repo.updateProfile(imageBitmap, username)))
        } catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }

}

class AuthViewModelFactory(private val repo: AuthRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AuthViewModel(repo) as T
    }
}