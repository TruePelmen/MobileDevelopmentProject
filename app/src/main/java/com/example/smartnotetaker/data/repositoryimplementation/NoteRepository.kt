package com.example.smartnotetaker.data.repositoryimplementation

import com.example.smartnotetaker.data.dao.NoteDAO
import com.example.smartnotetaker.data.entities.Note
import com.example.smartnotetaker.domain.repository.NoteRepository

class NoteRepositoryImpl(private val noteDao: NoteDAO) : NoteRepository {

    override suspend fun insert(note: Note) {
        return noteDao.insert(note)
    }

    override suspend fun getAll(): List<Note> {
        return noteDao.getAll()
    }

    override suspend fun delete(note: Note) {
        noteDao.delete(note)
    }

    override suspend fun getById(id: Long): Note {
        return noteDao.loadAllById(id)
    }

    override suspend fun deleteAll() {
        noteDao.deleteAll()
    }
}