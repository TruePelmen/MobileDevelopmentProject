package com.example.domain.usecase

import com.example.domain.models.Collection
import com.example.domain.repository.CollectionRepository

class SortCollectionsByNameUseCase(private val collectionRepository: CollectionRepository) {

    suspend operator fun invoke(): List<Collection> {
        val collections = collectionRepository.getAllCollections()
        return collections.sortedBy { it.name }
    }
}