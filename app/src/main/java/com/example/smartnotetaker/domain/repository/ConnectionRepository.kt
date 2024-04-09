package com.example.smartnotetaker.domain.repository

import com.example.smartnotetaker.domain.models.Connection

interface ConnectionRepository {

    suspend fun insertConnection(connection: Connection): Long

    suspend fun getAllConnections(): List<Connection>

    suspend fun getConnectionByNoteIds(note1Id: Long, note2Id: Long): Connection

    suspend fun deleteConnection(connection: Connection)
}
