package com.agustin.todoapp.data.dataresources

import com.agustin.todoapp.domain.entities.Note
import com.agustin.todoapp.tools.DataResources

interface IRemoteDataResource {
    suspend fun createUser(email: String, password: String)
    suspend fun signIn(email: String, password: String)
    suspend fun signOut()
    suspend fun getNotes(): DataResources<List<Note>>
    suspend fun addNote(note:Note)
    suspend fun getFullNote(noteId: String): DataResources<Note>
    suspend fun getUserId(): String
    suspend fun checkUserIsAuthenticated(): Boolean
    suspend fun removeNote(id: String)
}