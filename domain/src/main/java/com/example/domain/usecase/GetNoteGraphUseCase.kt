package com.example.domain.usecase

import com.example.domain.models.Note
import com.example.domain.models.Connection
import com.example.domain.repository.NoteRepository
import com.example.domain.repository.ConnectionRepository

class GetNoteGraphUseCase(
    private val noteRepository: NoteRepository,
    private val connectionRepository: ConnectionRepository
) {

    suspend operator fun invoke(collectionId: Long): Array<BooleanArray>? {
        // Отримати всі нотатки з певної колекції
        val notesInCollection = noteRepository.getAll().filter { it.collectionId == collectionId }

        if (notesInCollection.isEmpty()) {
            // Повернути null, якщо в колекції немає нотаток
            return null
        }

        // Отримати всі зв'язки між нотатками
        val allConnections = connectionRepository.getAllConnections()

        // Створити порожню матрицю суміжності
        val noteCount = notesInCollection.size
        val adjacencyMatrix = Array(noteCount) { BooleanArray(noteCount) }

        // Заповнити матрицю суміжності зв'язками між нотатками
        allConnections.forEach { connection ->
            val note1 = notesInCollection.find { it.id == connection.note1Id }
            val note2 = notesInCollection.find { it.id == connection.note2Id }
            if (note1 != null && note2 != null) {
                val note1Index = notesInCollection.indexOf(note1)
                val note2Index = notesInCollection.indexOf(note2)
                adjacencyMatrix[note1Index][note2Index] = true
                adjacencyMatrix[note2Index][note1Index] = true // Якщо граф неорієнтований
            }
        }

        return adjacencyMatrix
    }
}
