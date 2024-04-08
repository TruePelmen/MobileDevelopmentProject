package com.example.smartnotetaker.domain.repository

import com.example.smartnotetaker.data.entities.NoteEntity
import com.example.smartnotetaker.domain.models.Note


interface NoteRepository {

    suspend fun insert(note: NoteEntity)

    suspend fun getAll(): List<NoteEntity>

    suspend fun delete(note: NoteEntity)

    suspend fun getById(id: Long): NoteEntity

    suspend fun deleteAll()
}
