package com.example.smartnotetaker.repository

import com.example.smartnotetaker.data.entities.Note

interface NoteRepository {

    suspend fun insert(note: Note)

    suspend fun getAll(): List<Note>

    suspend fun delete(note: Note)

    suspend fun getById(id: Long): Note

    suspend fun deleteAll()
}
