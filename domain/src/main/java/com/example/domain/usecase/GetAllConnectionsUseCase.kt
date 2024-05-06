package com.example.domain.usecase

import com.example.domain.models.Connection
import com.example.domain.repository.ConnectionRepository

class GetAllConnectionsUseCase(private val connectionRepository: ConnectionRepository) {

    suspend operator fun invoke(): List<Connection> {
        return connectionRepository.getAllConnections()
    }
}