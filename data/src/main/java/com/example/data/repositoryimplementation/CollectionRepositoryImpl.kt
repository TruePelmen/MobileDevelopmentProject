package com.example.data.repositoryimplementation

import com.example.data.dao.CollectionDao
import com.example.data.entities.CollectionEntity
import com.example.domain.repository.CollectionRepository
import com.example.domain.models.Collection

class CollectionRepositoryImpl(private val collectionDao: CollectionDao) : CollectionRepository {

    override suspend fun insertCollection(collection: Collection) {
        val collectionEntity = collection.toEntity()
        collectionDao.insertCollection(collectionEntity)
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
