package com.example.smartnotetaker.data.repositoryimplementation

import com.example.smartnotetaker.data.dao.ConnectionDao
import com.example.smartnotetaker.data.entities.ConnectionEntity
import com.example.smartnotetaker.domain.repository.ConnectionRepository

class ConnectionRepositoryImpl(private val connectionDao: ConnectionDao) : ConnectionRepository {

    override suspend fun insertConnection(connectionEntity: ConnectionEntity): Long {
        return connectionDao.insertConnection(connectionEntity)
    }

    override suspend fun getAllConnections(): List<ConnectionEntity> {
        return connectionDao.getAllConnections()
    }

    override suspend fun getConnectionByNoteIds(note1Id: Long, note2Id: Long): ConnectionEntity {
        return connectionDao.getConnectionByNoteIds(note1Id, note2Id)
    }

    override suspend fun deleteConnection(connectionEntity: ConnectionEntity) {
        connectionDao.deleteConnection(connectionEntity)
    }
}