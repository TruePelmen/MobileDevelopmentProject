package com.example.domain.usecase

import com.example.domain.models.Note
import com.example.domain.repository.NoteRepository

class DeleteNoteUseCase(private val noteRepository: NoteRepository) {

    suspend fun execute(note: Note) {
        try {
            noteRepository.delete(note)
        } catch (e: Exception) {
            throw Exception("Failed to delete the note: ${e.message}")
        }
    }
}
