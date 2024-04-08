package com.example.smartnotetaker.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "connections")
data class Connection(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "note1_id")
    val note1Id: Long,
    @ColumnInfo(name = "note2_id")
    val note2Id: Long
)