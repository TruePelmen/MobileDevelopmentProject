package com.example.smartnotetaker.data.repositoryimplementation

import com.example.smartnotetaker.data.dao.ConnectionDao
import com.example.smartnotetaker.data.entities.Connection
import com.example.smartnotetaker.domain.repository.ConnectionRepository

class ConnectionRepositoryImpl(private val connectionDao: ConnectionDao) : ConnectionRepository {

    override suspend fun insertConnection(connection: Connection){
        return connectionDao.insertConnection(connection)
    }

    override suspend fun getAllConnections(): List<Connection> {
        return connectionDao.getAllConnections()
    }

    override suspend fun getConnectionByNoteIds(note1Id: Long, note2Id: Long): Connection {
        return connectionDao.getConnectionByNoteIds(note1Id, note2Id)
    }

    override suspend fun deleteConnection(connection: Connection) {
        connectionDao.deleteConnection(connection)
    }
}