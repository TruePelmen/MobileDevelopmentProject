package com.example.smartnotetaker.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "collections")
data class Collection(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String
)