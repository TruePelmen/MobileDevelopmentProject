package com.example.smartnotetaker.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.smartnotetaker.data.entities.Collection
import com.example.smartnotetaker.data.entities.Connection
import com.example.smartnotetaker.data.entities.Note
import com.example.smartnotetaker.data.dao.CollectionDao
import com.example.smartnotetaker.data.dao.ConnectionDao
import com.example.smartnotetaker.data.dao.NoteDAO

@Database(entities = [Note::class, Collection::class, Connection::class], version = 3)
abstract class MyDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDAO
    abstract fun connectionDao(): ConnectionDao
    abstract fun collectionDao(): CollectionDao
}