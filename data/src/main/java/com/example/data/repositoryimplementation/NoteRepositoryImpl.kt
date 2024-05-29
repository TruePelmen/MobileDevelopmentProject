package com.example.data.repositoryimplementation

import com.example.data.dao.NoteDAO
import com.example.data.entities.NoteEntity
import com.example.domain.models.Note
import com.example.domain.repository.NoteRepository

class NoteRepositoryImpl(private val noteDao: NoteDAO) : NoteRepository {

    override suspend fun insert(note: Note) {
        // Мапуємо з Note на NoteEntity перед вставкою в базу даних
        val noteEntity = note.toEntity()
        val noteList = noteDao.getAll()
        var hasSameName = false
        noteList.forEach{
            if (it.name == noteEntity.name)
                hasSameName = true
        }
        if (!hasSameName) {
            noteDao.insert(noteEntity)
        }
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

    override suspend fun update(note: Note){
        val noteEntity = note.toEntity()
        noteDao.update(noteEntity)
    }

    override suspend fun getById(id: Long): Note {
        // Отримуємо NoteEntity з бази даних і мапуємо його на модель Note
        return noteDao.loadAllById(id).toDomain()
    }

    override suspend fun deleteAll() {
        noteDao.deleteAll()
    }

    private fun NoteEntity.toDomain(): Note {
        return Note(id, name, text, nextRepetition, lastRepetition, creationDate, collectionId)
    }

    private fun Note.toEntity(): NoteEntity {
        return NoteEntity(id, name, text, nextRepetition, lastRepetition, creationDate, collectionId)
    }
}
