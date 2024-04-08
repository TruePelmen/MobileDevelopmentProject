package com.example.smartnotetaker.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.smartnotetaker.data.entities.CollectionEntity
import com.example.smartnotetaker.data.entities.ConnectionEntity
import com.example.smartnotetaker.data.entities.NoteEntity
import com.example.smartnotetaker.data.dao.CollectionDao
import com.example.smartnotetaker.data.dao.ConnectionDao
import com.example.smartnotetaker.data.dao.NoteDAO

@Database(entities = [NoteEntity::class, CollectionEntity::class, ConnectionEntity::class], version = 3)
abstract class MyDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDAO
    abstract fun connectionDao(): ConnectionDao
    abstract fun collectionDao(): CollectionDao
}