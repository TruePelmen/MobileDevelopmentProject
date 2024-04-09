package com.example.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.entities.ConnectionEntity

@Dao
interface ConnectionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertConnection(connectionEntity: ConnectionEntity):Long

    @Query("SELECT * FROM connections")
    fun getAllConnections(): List<ConnectionEntity>

    @Query("SELECT * FROM connections WHERE note1_id = :note1Id AND note2_id = :note2Id")
    fun getConnectionByNoteIds(note1Id: Long, note2Id: Long): ConnectionEntity

    @Delete
    fun deleteConnection(connectionEntity: ConnectionEntity)
}