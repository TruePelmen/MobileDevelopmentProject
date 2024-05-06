package com.example.domain.usecase

import com.example.domain.models.Connection
import com.example.domain.repository.ConnectionRepository

class DeleteConnectionUseCase(private val connectionRepository: ConnectionRepository) {

    suspend operator fun invoke(connection: Connection) {
        connectionRepository.deleteConnection(connection)
    }
}