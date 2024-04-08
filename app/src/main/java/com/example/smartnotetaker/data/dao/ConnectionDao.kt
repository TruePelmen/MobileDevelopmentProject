package com.example.smartnotetaker.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.smartnotetaker.data.entities.Connection

@Dao
interface ConnectionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertConnection(connection: Connection)

    @Query("SELECT * FROM connections")
    fun getAllConnections(): List<Connection>

    @Query("SELECT * FROM connections WHERE note1_id = :note1Id AND note2_id = :note2Id")
    fun getConnectionByNoteIds(note1Id: Long, note2Id: Long): Connection

    @Delete
    fun deleteConnection(connection: Connection)
}