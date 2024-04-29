package com.example.domain.usecase

import com.example.domain.models.Note
import com.example.domain.repository.NoteRepository


class EditNoteUseCase(private val noteRepository : NoteRepository) {
    suspend fun execute( note : Note){
        return noteRepository.update(note);
    }

}
