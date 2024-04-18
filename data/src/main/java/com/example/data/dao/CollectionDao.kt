package com.example.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.entities.CollectionEntity

@Dao
interface CollectionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCollection(collectionEntity: CollectionEntity)

    @Query("SELECT * FROM collections")
    fun getAllCollections(): List<CollectionEntity>

    @Query("SELECT * FROM collections WHERE id = :id")
    fun getCollectionById(id: Long): CollectionEntity

    @Update
    fun updateCollection(collectionEntity: CollectionEntity)

    @Delete
    fun deleteCollection(collectionEntity: CollectionEntity)

    @Query("DELETE FROM collections")
    fun deleteAllCollections()
}