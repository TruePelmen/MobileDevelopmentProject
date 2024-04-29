package com.example.domain.usecase

import com.example.domain.models.Collection
import com.example.domain.repository.CollectionRepository
import com.example.domain.repository.NoteRepository
import com.example.domain.repository.ConnectionRepository

class DeleteCollectionUseCase(
    private val collectionRepository: CollectionRepository,
    private val noteRepository: NoteRepository,
    private val connectionRepository: ConnectionRepository
) {

    suspend operator fun invoke(collection: Collection) {
        val notesToDelete = noteRepository.getAll().filter { it.collectionId == collection.id }

        notesToDelete.forEach { note ->
            val connectionsToDelete = connectionRepository.getAllConnections().filter { it.note1Id == note.id || it.note2Id == note.id }
            connectionsToDelete.forEach { connection ->
                connectionRepository.deleteConnection(connection)
            }
            noteRepository.delete(note)
        }
        collectionRepository.deleteCollection(collection)
    }
}
