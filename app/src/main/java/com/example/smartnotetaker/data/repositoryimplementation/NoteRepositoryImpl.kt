package com.example.smartnotetaker.data.repositoryimplementation

import com.example.smartnotetaker.data.dao.NoteDAO
import com.example.smartnotetaker.data.entities.NoteEntity
import com.example.smartnotetaker.domain.models.Note
import com.example.smartnotetaker.domain.repository.NoteRepository

class NoteRepositoryImpl(private val noteDao: NoteDAO) : NoteRepository {

    override suspend fun insert(note: Note) {
        // Мапуємо з Note на NoteEntity перед вставкою в базу даних
        val noteEntity = note.toEntity()
        noteDao.insert(noteEntity)
    }

    override suspend fun getAll(): List<Note> {
        // Отримуємо список NoteEntity з бази даних і мапуємо їх на модель Note
        return noteDao.getAll().map { it.toDomain() }
    }

    override suspend fun delete(note: Note) {
        // Мапуємо з Note на NoteEntity перед видаленням з бази даних
        val noteEntity = note.toEntity()
        noteDao.delete(noteEntity)
    }

    override suspend fun getById(id: Long): Note {
        // Отримуємо NoteEntity з бази даних і мапуємо його на модель Note
        return noteDao.loadAllById(id).toDomain()
    }

    override suspend fun deleteAll() {
        noteDao.deleteAll()
    }

    private fun NoteEntity.toDomain(): Note {
        return Note(id, name, text, nextRepetition, lastRepetition, collectionId)
    }

    private fun Note.toEntity(): NoteEntity {
        return NoteEntity(id, name, text, nextRepetition, lastRepetition, collectionId)
    }
}
