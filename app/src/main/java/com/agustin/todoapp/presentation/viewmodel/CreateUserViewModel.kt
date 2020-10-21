package com.agustin.todoapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.agustin.todoapp.domain.entities.User
import com.agustin.todoapp.domain.repository.IUserRepository
import com.agustin.todoapp.tools.DataResources
import kotlinx.coroutines.Dispatchers

class CreateUserViewModel: ViewModel() {
    lateinit var repository: IUserRepository

    fun createUser(email: String, password: String) = liveData(Dispatchers.IO) {
        emit(DataResources.Loading())
        try {
            repository.createUser(User(email, password))
            emit(DataResources.Success(true))
        }catch (error: Exception){
            emit(DataResources.Failure<Exception>(error))
        }
    }
}