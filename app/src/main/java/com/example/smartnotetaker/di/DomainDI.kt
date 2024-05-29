package com.example.smartnotetaker.di

import com.example.domain.usecase.CreateCollectionUseCase
import com.example.domain.usecase.CreateConnectionUseCase
import com.example.domain.usecase.CreateNoteUseCase
import com.example.domain.usecase.DeleteAllNotes
import com.example.domain.usecase.DeleteCollectionUseCase
import com.example.domain.usecase.DeleteConnectionUseCase
import com.example.domain.usecase.DeleteNoteUseCase
import com.example.domain.usecase.EditCollectionUseCase
import com.example.domain.usecase.GetAllConnectionsUseCase
import com.example.domain.usecase.GetAllNotesUseCase
import com.example.domain.usecase.GetNoteByIdUseCase
import com.example.domain.usecase.GetNoteGraphUseCase
import com.example.domain.usecase.SearchNotesByNameUseCase
import com.example.domain.usecase.SortCollectionsByDateUseCase
import com.example.domain.usecase.SortCollectionsByNameUseCase
import com.example.domain.usecase.UpdateNoteUseCase
import com.example.domain.usecase.ViewCollectionUseCase
import com.example.domain.usecase.ViewCollectionsUseCase
import org.koin.dsl.module

val domainModule = module{
    factory <CreateCollectionUseCase> {
        CreateCollectionUseCase(get())
    }
    factory <CreateConnectionUseCase> {
        CreateConnectionUseCase(get())
    }
    factory <CreateNoteUseCase> {
        CreateNoteUseCase(get())
    }
    factory <DeleteAllNotes> {
        DeleteAllNotes(get())
    }
    factory <DeleteCollectionUseCase> {
        DeleteCollectionUseCase(get(), get(), get())
    }
    factory <DeleteConnectionUseCase> {
        DeleteConnectionUseCase(get())
    }
    factory <DeleteNoteUseCase> {
        DeleteNoteUseCase(get())
    }
    factory <EditCollectionUseCase> {
        EditCollectionUseCase(get())
    }
    factory <GetAllConnectionsUseCase> {
        GetAllConnectionsUseCase(get())
    }

    factory <GetAllNotesUseCase> {
        GetAllNotesUseCase(get())
    }

    factory <GetNoteByIdUseCase> {
        GetNoteByIdUseCase(get())
    }

    factory <GetNoteGraphUseCase> {
        GetNoteGraphUseCase(get(), get())
    }

    factory <SearchNotesByNameUseCase> {
        SearchNotesByNameUseCase(get())
    }

    factory <SortCollectionsByDateUseCase> {
        SortCollectionsByDateUseCase(get())
    }

    factory <SortCollectionsByNameUseCase> {
        SortCollectionsByNameUseCase(get())
    }

    factory <ViewCollectionsUseCase> {
        ViewCollectionsUseCase(get())
    }

    factory <ViewCollectionUseCase> {
        ViewCollectionUseCase(get())
    }

    factory <UpdateNoteUseCase> {
        UpdateNoteUseCase(get())
    }
}