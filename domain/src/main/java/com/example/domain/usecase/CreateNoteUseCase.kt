package com.example.domain.usecase

import com.example.domain.models.Note
import com.example.domain.repository.NoteRepository

class CreateNoteUseCase(private val noteRepository: NoteRepository) {

    suspend operator fun invoke(note: Note) {
        return noteRepository.insert(note)
    }
}