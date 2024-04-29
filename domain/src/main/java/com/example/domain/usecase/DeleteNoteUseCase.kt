package com.example.domain.usecase

import com.example.domain.models.Note
import com.example.domain.repository.NoteRepository
import com.example.domain.repository.ConnectionRepository;

class DeleteNoteUseCase(private val noteRepository: NoteRepository,
                        private val connectionRepository: ConnectionRepository
) {

    suspend fun invoke(note: Note) {
        val connectionsToDelete = connectionRepository.getAllConnections()
            .filter { it.note1Id == note.id || it.note2Id == note.id }

        connectionsToDelete.forEach { connection ->
            connectionRepository.deleteConnection(connection)
        }

        noteRepository.delete(note)
    }
}
