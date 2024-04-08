package com.example.smartnotetaker.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.smartnotetaker.data.entities.NoteEntity

@Dao
interface NoteDAO {
    @Insert
    fun insert (noteEntity: NoteEntity);
    @Query("SELECT * FROM note")
    fun getAll(): List<NoteEntity>
    @Delete
    fun delete(noteEntity: NoteEntity)
    @Query("SELECT * FROM note WHERE id IN (:noteId)")
    fun loadAllById(noteId: Long): NoteEntity
    @Query("DELETE FROM note")
    fun deleteAll()
}