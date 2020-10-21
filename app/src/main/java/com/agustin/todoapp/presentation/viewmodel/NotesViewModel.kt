package com.agustin.todoapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.agustin.todoapp.domain.repository.INoteRepository
import com.agustin.todoapp.domain.repository.IUserRepository
import com.agustin.todoapp.tools.DataResources
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel : ViewModel() {
    lateinit var noteRepository: INoteRepository
    lateinit var userRepository: IUserRepository

    fun getNotes() = liveData(Dispatchers.IO) {
        emit(DataResources.Loading())
        try {
            Log.d("VM", "try")
            emit(noteRepository.getNotes())
            Log.d("VM", "AfterTry")
        } catch (error: Exception) {
            emit(DataResources.Failure<Exception>(error))
        }
    }

    fun signOut() {
        viewModelScope.launch(Dispatchers.IO) { userRepository.signOut() }
    }
}