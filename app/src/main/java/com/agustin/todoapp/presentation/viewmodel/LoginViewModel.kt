package com.agustin.todoapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.agustin.todoapp.domain.repository.IUserRepository
import com.agustin.todoapp.domain.entities.User
import com.agustin.todoapp.tools.DataResources

class LoginViewModel(): ViewModel() {
    lateinit var repository: IUserRepository
    fun logIn(user: User) = liveData {
        emit(DataResources.Loading())
        try {
            repository.signIn(user)
            emit(DataResources.Success(true))
        }catch (error: Exception){
            emit(DataResources.Failure<Exception>(error))
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("Fragment", "Cleared")
    }
}