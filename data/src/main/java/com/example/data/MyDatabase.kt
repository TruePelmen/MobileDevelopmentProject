package com.example.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.entities.CollectionEntity
import com.example.data.entities.ConnectionEntity
import com.example.data.entities.NoteEntity
import com.example.data.dao.CollectionDao
import com.example.data.dao.ConnectionDao
import com.example.data.dao.NoteDAO

@Database(entities = [NoteEntity::class, CollectionEntity::class, ConnectionEntity::class], version = 5)
abstract class MyDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDAO
    abstract fun connectionDao(): ConnectionDao
    abstract fun collectionDao(): CollectionDao
}