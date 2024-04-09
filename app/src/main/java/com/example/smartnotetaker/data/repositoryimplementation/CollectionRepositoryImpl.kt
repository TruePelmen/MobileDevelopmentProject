package com.example.smartnotetaker.data.repositoryimplementation

import com.example.smartnotetaker.data.dao.CollectionDao
import com.example.smartnotetaker.data.entities.CollectionEntity
import com.example.smartnotetaker.domain.models.Collection
import com.example.smartnotetaker.domain.repository.CollectionRepository

class CollectionRepositoryImpl(private val collectionDao: CollectionDao) : CollectionRepository {

    override suspend fun insertCollection(collection: Collection): Long {
        val collectionEntity = collection.toEntity()
        return collectionDao.insertCollection(collectionEntity)
    }

    override suspend fun getAllCollections(): List<Collection> {
        return collectionDao.getAllCollections().map { it.toDomain() }
    }

    override suspend fun getCollectionById(id: Long): Collection {
        return collectionDao.getCollectionById(id).toDomain()
    }

    override suspend fun updateCollection(collection: Collection) {
        val collectionEntity = collection.toEntity()
        collectionDao.updateCollection(collectionEntity)
    }

    override suspend fun deleteCollection(collection: Collection) {
        val collectionEntity = collection.toEntity()
        collectionDao.deleteCollection(collectionEntity)
    }

    override suspend fun deleteAllCollections() {
        collectionDao.deleteAllCollections()
    }

    private fun CollectionEntity.toDomain(): Collection {
        return Collection(id, name)
    }

    private fun Collection.toEntity(): CollectionEntity {
        return CollectionEntity(id, name)
    }
}
