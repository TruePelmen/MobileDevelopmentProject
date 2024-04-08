package com.example.smartnotetaker.domain.repository
import com.example.smartnotetaker.data.entities.CollectionEntity
interface CollectionRepository {

    suspend fun insertCollection(collectionEntity: CollectionEntity):Long

    suspend fun getAllCollections(): List<CollectionEntity>

    suspend fun getCollectionById(id: Long): CollectionEntity

    suspend fun updateCollection(collectionEntity: CollectionEntity)

    suspend fun deleteCollection(collectionEntity: CollectionEntity)

    suspend fun deleteAllCollections()
}