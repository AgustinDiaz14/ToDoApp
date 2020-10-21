package com.agustin.todoapp.domain.entities

data class Note(
    val title: String,
    val tasks: List<NoteTask>,
)