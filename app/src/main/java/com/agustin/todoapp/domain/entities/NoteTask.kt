package com.agustin.todoapp.domain.entities

data class NoteTask(
    val content: String,
    var isCompleted: Boolean
)