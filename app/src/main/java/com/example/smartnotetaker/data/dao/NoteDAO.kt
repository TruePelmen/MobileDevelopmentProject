package com.example.smartnotetaker.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.smartnotetaker.data.entities.Note

@Dao
interface NoteDAO {
    @Insert
    fun insert (note: Note);
    @Query("SELECT * FROM note")
    fun getAll(): List<Note>
    @Delete
    fun delete(note: Note)
    @Query("SELECT * FROM note WHERE id IN (:noteId)")
    fun loadAllById(noteId: Long): Note
    @Query("DELETE FROM note")
    fun deleteAll()
}