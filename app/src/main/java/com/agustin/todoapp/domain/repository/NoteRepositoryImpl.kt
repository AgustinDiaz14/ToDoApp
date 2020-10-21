package com.agustin.todoapp.domain.repository

import android.util.Log
import com.agustin.todoapp.data.dataresources.IRemoteDataResource
import com.agustin.todoapp.domain.entities.Note
import com.agustin.todoapp.tools.DataResources

class NoteRepositoryImpl(
    //private val localDataResource: ILocalDataResource,
    private val remoteDataResource: IRemoteDataResource
) : INoteRepository {
    override suspend fun getNotes(): DataResources<List<Note>>{
        val notes = remoteDataResource.getNotes()
        Log.d("VM", notes.toString())
        return notes
    }

    override suspend fun addNote(note: Note) {
        remoteDataResource.addNote(note)
    }

    override suspend fun removeNote(noteId: String) {
        remoteDataResource.removeNote(noteId)
    }

    override suspend fun getFullNote(id: String): DataResources<Note> = remoteDataResource.getFullNote(id)
}