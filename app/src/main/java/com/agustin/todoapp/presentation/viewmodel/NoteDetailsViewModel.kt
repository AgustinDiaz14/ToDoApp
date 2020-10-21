package com.agustin.todoapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.agustin.todoapp.domain.entities.Note
import com.agustin.todoapp.domain.repository.INoteRepository
import com.agustin.todoapp.tools.DataResources
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteDetailsViewModel: ViewModel() {
    lateinit var repository: INoteRepository
    fun getNote(noteId: String) = liveData(Dispatchers.IO) {
        emit(DataResources.Loading())
        try {
            emit(repository.getFullNote(noteId))
        }catch (error: Exception){
            emit(DataResources.Failure<Exception>(error))
        }
    }

    fun updateData(note: Note){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addNote(note)
        }
    }

    fun deleteNote(noteTitle: String){
        viewModelScope.launch(Dispatchers.IO) { repository.removeNote(noteTitle) }
    }
}