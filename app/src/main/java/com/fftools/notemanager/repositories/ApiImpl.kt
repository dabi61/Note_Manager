package com.fftools.notemanager.repositories

import com.fftools.notemanager.model.NoteItem
import kotlinx.coroutines.delay
import javax.inject.Inject


class ApiImpl @Inject constructor() : Api {
    var notes = ArrayList<NoteItem>()

    init {
        // Initialize with some dummy data
        notes.add(NoteItem(1, "Note 1", "Content of Note 1"))
        notes.add(NoteItem(2, "Note 2", "Content of Note 2"))
        notes.add(NoteItem(3, "Note 3", "Content of Note 3"))
    }


    override suspend fun login(username: String, password: String): Boolean {
        delay(1000) // gia lap time delay login
        if (username != "1" || password != "1") {
            throw Exception("Wrong credentials")
        }
        return true
    }

    override suspend fun loadNotes(): List<NoteItem> {
        delay(1000)
        return notes
    }

    override suspend fun addNote(title: String, content: String) {
        delay(1000)
        notes.add(NoteItem(System.currentTimeMillis(), title, content))
    }

    override suspend fun updateNote(id: Long, title: String, content: String) {
        delay(1000)
        for (note in notes.indices) {
            if(notes[note].id == id) {
                notes[note] = NoteItem(id, title, content)
                return
            }
        }
    }

    override suspend fun deleteNote(noteId: Long) {
        delay(1000)
        for (note in notes.indices) {
            if(notes[note].id == noteId) {
                notes.removeAt(note)
                return
            }
        }
    }
}