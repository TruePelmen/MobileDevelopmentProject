package com.example.smartnotetaker.data.repositoryimplementation

import com.example.smartnotetaker.data.dao.ConnectionDao
import com.example.smartnotetaker.data.entities.ConnectionEntity
import com.example.smartnotetaker.domain.models.Connection
import com.example.smartnotetaker.domain.repository.ConnectionRepository

class ConnectionRepositoryImpl(private val connectionDao: ConnectionDao) : ConnectionRepository {

    override suspend fun insertConnection(connection: Connection): Long {
        val connectionEntity = connection.toEntity()
        return connectionDao.insertConnection(connectionEntity)
    }

    override suspend fun getAllConnections(): List<Connection> {
        return connectionDao.getAllConnections().map { it.toDomain() }
    }

    override suspend fun getConnectionByNoteIds(note1Id: Long, note2Id: Long): Connection {
        return connectionDao.getConnectionByNoteIds(note1Id, note2Id).toDomain()
    }

    override suspend fun deleteConnection(connection: Connection) {
        val connectionEntity = connection.toEntity()
        connectionDao.deleteConnection(connectionEntity)
    }

    private fun ConnectionEntity.toDomain(): Connection {
        return Connection(id, note1Id, note2Id)
    }

    private fun Connection.toEntity(): ConnectionEntity {
        return ConnectionEntity(id, note1Id, note2Id)
    }
}
