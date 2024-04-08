package com.example.smartnotetaker.domain.repository

import com.example.smartnotetaker.data.entities.ConnectionEntity

interface ConnectionRepository {

    suspend fun insertConnection(connectionEntity: ConnectionEntity):Long

    suspend fun getAllConnections(): List<ConnectionEntity>

    suspend fun getConnectionByNoteIds(note1Id: Long, note2Id: Long): ConnectionEntity

    suspend fun deleteConnection(connectionEntity: ConnectionEntity)
}