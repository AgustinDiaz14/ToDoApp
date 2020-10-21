package com.agustin.todoapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.agustin.todoapp.data.dataresources.firebase.FirebaseDataResource
import com.agustin.todoapp.domain.entities.Note
import com.agustin.todoapp.domain.repository.INoteRepository
import com.agustin.todoapp.domain.repository.NoteRepositoryImpl
import com.agustin.todoapp.tools.DataResources
import kotlinx.coroutines.Dispatchers

class CreateNoteViewModel(): ViewModel() {
    lateinit var repository: INoteRepository
    fun createNote(note: Note)= liveData(Dispatchers.IO){
        emit(DataResources.Loading())
        try {
            repository.addNote(note)
            emit(DataResources.Success(true))
        }catch (error: Exception){
            emit(DataResources.Failure<Exception>(error))
        }
    }
}