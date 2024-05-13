package com.example.smartnotetaker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.Collection
import com.example.domain.usecase.CreateCollectionUseCase
import com.example.domain.usecase.DeleteCollectionUseCase
import com.example.domain.usecase.EditCollectionUseCase
import com.example.domain.usecase.ViewCollectionUseCase
import com.example.domain.usecase.ViewCollectionsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val createCollectionUseCase: CreateCollectionUseCase,
    private val deleteCollectionUseCase: DeleteCollectionUseCase,
    private val editCollectionUseCase: EditCollectionUseCase,
    private val viewCollectionsUseCase: ViewCollectionsUseCase,
    private val viewCollectionUseCase: ViewCollectionUseCase
) : ViewModel() {
    private val _collectionsUiState = MutableStateFlow(CollectionsUiState())
    var collectionsUiState: StateFlow<CollectionsUiState> = _collectionsUiState.asStateFlow()

    // Додати нову колекцію
    fun addCollection(collection: Collection) {
        viewModelScope.launch(Dispatchers.IO) {
            createCollectionUseCase(collection)
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
        }
    }

    // Редагувати колекцію
    fun editCollection(collection: Collection) {
        viewModelScope.launch(Dispatchers.IO) {
            editCollectionUseCase(collection)
        }
    }

    // Отримати список колекцій з бази даних
    fun loadCollections() {
        viewModelScope.launch(Dispatchers.IO) {
            collectionsUiState.value.collections.clear()
            collectionsUiState.value.collections.addAll(viewCollectionsUseCase())
        }
    }
    // Define ViewModel factory in a companion object
//    companion object {
//
//        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
//            @Suppress("UNCHECKED_CAST")
//            override fun <T : ViewModel> create(
//                modelClass: Class<T>,
//                extras: CreationExtras
//            ): T {
//                // Get the Application object from extras
//                val application = checkNotNull(extras[APPLICATION_KEY])
//                // Create a SavedStateHandle for this ViewModel from extras
//                val savedStateHandle = extras.createSavedStateHandle()
//
//                return MainViewModel(
//                    (application as MyApplication).myRepository,
//                    savedStateHandle
//                ) as T
//            }
//        }
//    }
}

