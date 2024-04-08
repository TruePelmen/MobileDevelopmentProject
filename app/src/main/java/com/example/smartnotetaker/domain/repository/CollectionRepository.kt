package com.example.smartnotetaker.domain.repository
import com.example.smartnotetaker.data.entities.Collection
interface CollectionRepository {

    suspend fun insertCollection(collection: Collection)

    suspend fun getAllCollections(): List<Collection>

    suspend fun getCollectionById(id: Long): Collection

    suspend fun updateCollection(collection: Collection)

    suspend fun deleteCollection(collection: Collection)

    suspend fun deleteAllCollections()
}