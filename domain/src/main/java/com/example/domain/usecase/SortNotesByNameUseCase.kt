package com.example.domain.usecase

import com.example.domain.models.Note
import com.example.domain.repository.NoteRepository

class SortNotesByNameUseCase(private val noteRepository: NoteRepository) {
    suspend operator fun invoke(): List<Note> {
        val notes = noteRepository.getAll()
        return notes.sortedBy { it.name }
    }
}