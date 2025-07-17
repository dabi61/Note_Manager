package com.fftools.notemanager.repositories

import com.fftools.notemanager.model.NoteItem

interface Api {
    suspend fun login(username: String, password: String): Boolean
    suspend fun loadNotes(): List<NoteItem>
    suspend fun addNote(title: String, content: String)
    suspend fun updateNote(id: Long, title: String, content: String)
    suspend fun deleteNote(noteId: Long)
}