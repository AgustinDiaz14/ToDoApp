package com.agustin.todoapp.domain.repository

import com.agustin.todoapp.domain.entities.Note
import com.agustin.todoapp.tools.DataResources

interface INoteRepository {
    suspend fun getNotes(): DataResources<List<Note>>
    suspend fun addNote(note: Note)
    suspend fun removeNote(noteId: String)
    suspend fun getFullNote(id: String): DataResources<Note>
}