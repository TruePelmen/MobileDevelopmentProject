package com.example.domain.usecase

import com.example.domain.models.Collection
import com.example.domain.repository.CollectionRepository

class DeleteCollectionUseCase(private val collectionRepository: CollectionRepository) {

    suspend operator fun invoke(collection: Collection) {
        return collectionRepository.deleteCollection(collection)
    }
}