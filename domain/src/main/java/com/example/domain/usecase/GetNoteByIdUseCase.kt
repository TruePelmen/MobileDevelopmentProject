package com.example.domain.usecase

import com.example.domain.models.Note
import com.example.domain.repository.NoteRepository

class GetNoteByIdUseCase(private val noteRepository: NoteRepository) {
    suspend fun invoke(id: Long): Note {
        return try {
            noteRepository.getById(id)
        } catch (e: Exception) {
            throw Exception("Failed to retrieve note with ID: $id", e)
        }
    }
}