package com.example.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.data.SqlDateConverter
import java.sql.Date

@Entity(tableName = "note")
@TypeConverters(SqlDateConverter::class)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val text: String,
    @ColumnInfo(name = "next_repetition")
    val nextRepetition: Date = Date(1648062400000L), // <- Стандартне значення
    @ColumnInfo(name = "last_repetition")
    val lastRepetition: Date = Date(1648062400000L), // <- Стандартне значення
    @ColumnInfo(name = "creation_date")
    val creationDate: Date = Date(1648062400000L),
    @ColumnInfo(name = "collection_id")
    val collectionId: Long
)