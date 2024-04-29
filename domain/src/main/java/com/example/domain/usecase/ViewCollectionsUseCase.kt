package com.example.domain.usecase

import com.example.domain.models.Collection
import com.example.domain.repository.CollectionRepository

class ViewCollectionsUseCase(private val collectionRepository: CollectionRepository) {

    suspend operator fun invoke() : List<Collection> {
        return collectionRepository.getAllCollections()
    }
}