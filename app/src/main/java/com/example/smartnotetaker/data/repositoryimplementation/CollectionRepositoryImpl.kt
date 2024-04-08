package com.example.smartnotetaker.data.repositoryimplementation

import com.example.smartnotetaker.data.dao.CollectionDao
import com.example.smartnotetaker.repository.CollectionRepository
import com.example.smartnotetaker.data.entities.Collection

class CollectionRepositoryImpl(private val collectionDao: CollectionDao) : CollectionRepository {

    override suspend fun insertCollection(collection: Collection){
        return collectionDao.insertCollection(collection)
    }

    override suspend fun getAllCollections(): List<Collection> {
        return collectionDao.getAllCollections()
    }

    override suspend fun getCollectionById(id: Long): Collection {
        return collectionDao.getCollectionById(id)
    }

    override suspend fun updateCollection(collection: Collection) {
        collectionDao.updateCollection(collection)
    }

    override suspend fun deleteCollection(collection: Collection) {
        collectionDao.deleteCollection(collection)
    }

    override suspend fun deleteAllCollections() {
        collectionDao.deleteAllCollections()
    }
}