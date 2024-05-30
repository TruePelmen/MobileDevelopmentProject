package com.example.smartnotetaker.uistates

import com.example.domain.models.Collection

data class CollectionsUiState(
    var collections: List<Collection> = mutableListOf()
)
