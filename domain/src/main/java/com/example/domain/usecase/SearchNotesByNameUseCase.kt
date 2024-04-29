package com.example.domain.usecase

import com.example.domain.models.Note
import com.example.domain.repository.NoteRepository

class SearchNotesByNameUseCase(private val noteRepository: NoteRepository) {

    suspend operator fun invoke(query: String): List<Note> {
        return noteRepository.getAll().filter { note ->
            note.name.contains(query, ignoreCase = true)
        }
    }
}
