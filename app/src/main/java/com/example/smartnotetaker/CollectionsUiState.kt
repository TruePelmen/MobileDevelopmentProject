package com.example.smartnotetaker

import com.example.domain.models.Collection

data class CollectionsUiState(
    var collections: MutableList<Collection> = mutableListOf<Collection>()
)
