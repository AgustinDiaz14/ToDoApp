package com.agustin.todoapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.agustin.todoapp.domain.repository.IUserRepository
import com.agustin.todoapp.tools.DataResources
import kotlinx.coroutines.Dispatchers

class SplashViewModel: ViewModel() {
    lateinit var repository: IUserRepository

    fun checkUser() = liveData(Dispatchers.IO) {
        emit(DataResources.Loading())
        try {
            emit(DataResources.Success(repository.checkIfUserIsAuthenticated()))
        }catch (error: Exception){
            emit(DataResources.Failure<Exception>(error))
        }
    }

}