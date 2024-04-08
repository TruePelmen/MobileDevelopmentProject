package com.example.smartnotetaker.data.repositoryimplementation

import com.example.smartnotetaker.data.dao.NoteDAO
import com.example.smartnotetaker.data.entities.NoteEntity
import com.example.smartnotetaker.domain.repository.NoteRepository

class NoteRepositoryImpl(private val noteDao: NoteDAO) : NoteRepository {

    override suspend fun insert(noteEntity: NoteEntity) {
        return noteDao.insert(noteEntity)
    }

    override suspend fun getAll(): List<NoteEntity> {
        return noteDao.getAll()
    }

    override suspend fun delete(noteEntity: NoteEntity) {
        noteDao.delete(noteEntity)
    }

    override suspend fun getById(id: Long): NoteEntity {
        return noteDao.loadAllById(id)
    }

    override suspend fun deleteAll() {
        noteDao.deleteAll()
    }
}