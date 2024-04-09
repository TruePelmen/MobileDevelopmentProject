package com.example.smartnotetaker.domain.usecase

import com.example.smartnotetaker.data.entities.NoteEntity
import com.example.smartnotetaker.domain.models.Note
import com.example.smartnotetaker.domain.repository.NoteRepository

class CreateNoteUseCase(private val noteRepository: NoteRepository) {

    suspend operator fun invoke(note: Note) {
        return noteRepository.insert(note)
    }
}