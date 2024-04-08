package com.example.smartnotetaker.domain.repository

import com.example.smartnotetaker.data.entities.Connection

interface ConnectionRepository {

    suspend fun insertConnection(connection: Connection)

    suspend fun getAllConnections(): List<Connection>

    suspend fun getConnectionByNoteIds(note1Id: Long, note2Id: Long): Connection

    suspend fun deleteConnection(connection: Connection)
}