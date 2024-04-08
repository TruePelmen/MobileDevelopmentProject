package com.example.smartnotetaker.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.smartnotetaker.data.entities.Collection

@Dao
interface CollectionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCollection(collection: Collection)

    @Query("SELECT * FROM collections")
    fun getAllCollections(): List<Collection>

    @Query("SELECT * FROM collections WHERE id = :id")
    fun getCollectionById(id: Long): Collection

    @Update
    fun updateCollection(collection: Collection)

    @Delete
    fun deleteCollection(collection: Collection)

    @Query("DELETE FROM collections")
    fun deleteAllCollections()
}