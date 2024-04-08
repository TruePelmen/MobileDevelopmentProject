package com.example.smartnotetaker.domain.repository

import com.example.smartnotetaker.domain.models.Note


interface NoteRepository {

    suspend fun insert(note: Note)

    suspend fun getAll(): List<Note>

    suspend fun delete(note: Note)

    suspend fun getById(id: Long): Note

    suspend fun deleteAll()
}
