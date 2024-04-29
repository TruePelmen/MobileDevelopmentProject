package com.example.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.data.entities.NoteEntity

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
    @Update
    fun update(noteEntity: NoteEntity)
}