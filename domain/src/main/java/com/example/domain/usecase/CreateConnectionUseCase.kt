package com.example.domain.usecase

import com.example.domain.models.Connection
import com.example.domain.repository.ConnectionRepository

class CreateConnectionUseCase(private val connectionRepository: ConnectionRepository) {

    suspend operator fun invoke(note1Id: Long, note2Id: Long): Long {
        val connection = Connection(0, note1Id, note2Id)
        return connectionRepository.insertConnection(connection)
    }
}