package com.example.domain.usecase

import com.example.domain.models.Collection
import com.example.domain.repository.CollectionRepository

class ViewCollectionUseCase(private val collectionRepository: CollectionRepository) {

    suspend operator fun invoke(collectionId: Long): Collection {
        return collectionRepository.getCollectionById(collectionId)
    }
}