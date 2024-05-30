package com.example.smartnotetaker

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.Collection
import com.example.domain.models.Note
import com.example.domain.usecase.CreateCollectionUseCase
import com.example.domain.usecase.CreateNoteUseCase
import com.example.domain.usecase.DeleteCollectionUseCase
import com.example.domain.usecase.DeleteNoteUseCase
import com.example.domain.usecase.EditCollectionUseCase
import com.example.domain.usecase.GetAllNotesUseCase
import com.example.domain.usecase.GetNoteByIdUseCase
import com.example.domain.usecase.UpdateNoteUseCase
import com.example.domain.usecase.ViewCollectionsUseCase
import com.example.smartnotetaker.uistates.CollectionsUiState
import com.example.smartnotetaker.uistates.NotesUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val createCollectionUseCase: CreateCollectionUseCase,
    private val deleteCollectionUseCase: DeleteCollectionUseCase,
    private val editCollectionUseCase: EditCollectionUseCase,
    private val viewCollectionsUseCase: ViewCollectionsUseCase,
    private val viewNoteUseCase: GetNoteByIdUseCase,
    private val getAllNotesUseCase: GetAllNotesUseCase,
    private val createNoteUseCase: CreateNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
) : ViewModel() {
    private var collectionsUiState = mutableStateOf(CollectionsUiState())
    private var notesUiState = mutableStateOf(NotesUiState())

    private val _noteState = mutableStateOf<Note?>(null)
    val noteState = _noteState
    init {
        loadCollections()
    }
    // Додати нову колекцію
    fun addCollection(collection: Collection) {
        viewModelScope.launch(Dispatchers.IO) {
            createCollectionUseCase(collection)
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

    fun getNotesForCollection(collectionId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val notes = getAllNotesUseCase().filter { it.collectionId.toString() == collectionId }
            notesUiState.value.notes = notes
        }
    }

    fun getNotesUiState(): List<Note> {
        return notesUiState.value.notes
    }

    fun addNote(note: Note, collectionId: String){
        viewModelScope.launch(Dispatchers.IO) {
            createNoteUseCase.invoke(note)
            val notes = getAllNotesUseCase().filter { it.collectionId.toString() == collectionId }
            notesUiState.value.notes = notes
        }
    }

    fun getNoteById(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val note = viewNoteUseCase.invoke(id)
            _noteState.value = note
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            updateNoteUseCase.invoke(note)
            _noteState.value = note
        }
    }

    fun deleteNoteById(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteNoteUseCase.execute(viewNoteUseCase.invoke(id))
            _noteState.value = null
        }
    }
}

