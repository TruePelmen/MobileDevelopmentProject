package com.example.smartnotetaker

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.Collection
import com.example.domain.usecase.CreateCollectionUseCase
import com.example.domain.usecase.DeleteCollectionUseCase
import com.example.domain.usecase.EditCollectionUseCase
import com.example.domain.usecase.ViewCollectionUseCase
import com.example.domain.usecase.ViewCollectionsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val createCollectionUseCase: CreateCollectionUseCase,
    private val deleteCollectionUseCase: DeleteCollectionUseCase,
    private val editCollectionUseCase: EditCollectionUseCase,
    private val viewCollectionsUseCase: ViewCollectionsUseCase,
    private val viewCollectionUseCase: ViewCollectionUseCase
) : ViewModel() {
    private var collectionsUiState = mutableStateOf(CollectionsUiState())

    init {
        loadCollections()
    }
    // Додати нову колекцію
    fun addCollection(collectionName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val newCollection = Collection(name = collectionName)
            createCollectionUseCase(newCollection)
            collectionsUiState.value.collections = getCollections()
            // Update UI state after successful creation
            loadCollections()
        }
    }

    // Отримати список колекцій
    fun getCollections(): List<Collection> {
        return collectionsUiState.value.collections.toList()
    }

    // Видалити колекцію
    fun deleteCollection(collection: Collection) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteCollectionUseCase(collection)
            loadCollections() // Оновити список після видалення
        }
    }

    // Редагувати колекцію
    fun editCollection(collection: Collection) {
        viewModelScope.launch(Dispatchers.IO) {
            editCollectionUseCase(collection)
            loadCollections() // Оновити список після редагування
        }
    }

    // Отримати список колекцій з бази даних
    fun loadCollections() {
        viewModelScope.launch(Dispatchers.IO) {
            val collections = viewCollectionsUseCase()
            collectionsUiState.value = collectionsUiState.value.copy(collections = collections)
        }
    }

    // Отримати колекцію за ID
    fun getCollectionById(collectionId: String): Collection? {
        return collectionsUiState.value.collections.find { it.id.toString() == collectionId }
    }


}

