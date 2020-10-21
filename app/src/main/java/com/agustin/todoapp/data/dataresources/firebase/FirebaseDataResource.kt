package com.agustin.todoapp.data.dataresources.firebase

import android.util.Log
import com.agustin.todoapp.data.dataresources.IRemoteDataResource
import com.agustin.todoapp.domain.entities.Note
import com.agustin.todoapp.domain.entities.NoteTask
import com.agustin.todoapp.tools.DataResources
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

object FirebaseDataResource : IRemoteDataResource {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    override suspend fun createUser(email: String, password: String): Unit = suspendCancellableCoroutine { cancelableCoroutine ->
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    cancelableCoroutine.resume(Unit)
                }
                .addOnFailureListener {
                    cancelableCoroutine.resumeWithException(it)
                }
        }

    override suspend fun signIn(email: String, password: String): Unit = suspendCancellableCoroutine { cancelableCoroutine ->
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    cancelableCoroutine.resume(Unit)
                }
                .addOnFailureListener {
                    cancelableCoroutine.resumeWithException(it)
                }
        }

    override suspend fun signOut() {
        auth.signOut()
    }

    override suspend fun getNotes(): DataResources<List<Note>> {
        val listOfNotes = arrayListOf<Note>()

        firestore.collection(getUserId()).get().await().forEach { document ->

            listOfNotes.add(
                Note(
                    document.data["title"].toString(),
                    document.data["tasks"] as List<NoteTask>
                )
            )
        }

        return DataResources.Success(listOfNotes)
    }

    override suspend fun addNote(note: Note) {
        firestore.collection(getUserId()).document(note.title).set(note)
    }

    override suspend fun getFullNote(noteId: String): DataResources<Note> {
        var note: Note
        firestore.collection(getUserId()).document(noteId).get().await()
            .apply {
                note = Note(this["title"].toString(), this["tasks"] as List<NoteTask>)
                Log.d("bundle", note.toString())
            }

        return DataResources.Success(note)
    }

    override suspend fun getUserId(): String {
        return auth.uid!!
    }

    override suspend fun checkUserIsAuthenticated(): Boolean {
        return auth.currentUser != null
    }

    override suspend fun removeNote(id: String) {
        firestore.collection(getUserId()).document(id).delete()
    }
}