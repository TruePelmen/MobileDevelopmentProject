package com.example.domain.usecase

import com.example.domain.models.Note
import com.example.domain.repository.NoteRepository

class UpdateNoteUseCase (private val noteRepository: NoteRepository) {

    suspend operator fun invoke(note: Note) {
        return noteRepository.update(note)
    }
}