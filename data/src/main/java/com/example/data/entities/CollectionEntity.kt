package com.example.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.data.SqlDateConverter
import java.sql.Date

@Entity(tableName = "collections")
@TypeConverters(SqlDateConverter::class)
data class CollectionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    @ColumnInfo(name = "creation_date")
    val creationDate: Date = Date(1648062400000L)
)