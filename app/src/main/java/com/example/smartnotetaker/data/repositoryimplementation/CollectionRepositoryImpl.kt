package com.example.smartnotetaker.data.repositoryimplementation

import com.example.smartnotetaker.data.dao.CollectionDao
import com.example.smartnotetaker.domain.repository.CollectionRepository
import com.example.smartnotetaker.data.entities.CollectionEntity

class CollectionRepositoryImpl(private val collectionDao: CollectionDao) : CollectionRepository {

    override suspend fun insertCollection(collectionEntity: CollectionEntity): Long {
        return collectionDao.insertCollection(collectionEntity)
    }

    override suspend fun getAllCollections(): List<CollectionEntity> {
        return collectionDao.getAllCollections()
    }

    override suspend fun getCollectionById(id: Long): CollectionEntity {
        return collectionDao.getCollectionById(id)
    }

    override suspend fun updateCollection(collectionEntity: CollectionEntity) {
        collectionDao.updateCollection(collectionEntity)
    }

    override suspend fun deleteCollection(collectionEntity: CollectionEntity) {
        collectionDao.deleteCollection(collectionEntity)
    }

    override suspend fun deleteAllCollections() {
        collectionDao.deleteAllCollections()
    }
}