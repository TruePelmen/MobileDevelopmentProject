package com.example.smartnotetaker.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.smartnotetaker.data.SqlDateConverter
import java.sql.Date

@Entity(tableName = "note")
@TypeConverters(SqlDateConverter::class)
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val text: String,
    @ColumnInfo(name = "next_repetition")
    val nextRepetition: Date = Date(1648062400000L), // <- Стандартне значення
    @ColumnInfo(name = "last_repetition")
    val lastRepetition: Date = Date(1648062400000L), // <- Стандартне значення
    @ColumnInfo(name = "collection_id")
    val collectionId: Long
)