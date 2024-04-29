package com.example.domain.usecase

import com.example.domain.models.Note
import com.example.domain.repository.NoteRepository

class GetAllNotesUseCase(private val noteRepository: NoteRepository) {

    suspend operator fun invoke() : List<Note>{
        return noteRepository.getAll()
    }
}