package com.example.smartnotetaker.di

import android.app.Application
import androidx.room.Room
import com.example.data.MyDatabase
import com.example.data.repositoryimplementation.CollectionRepositoryImpl
import com.example.data.repositoryimplementation.ConnectionRepositoryImpl
import com.example.data.repositoryimplementation.NoteRepositoryImpl
import com.example.domain.repository.CollectionRepository
import com.example.domain.repository.ConnectionRepository
import com.example.domain.repository.NoteRepository
import org.koin.dsl.module

fun provideDataBase(application: Application) = Room.databaseBuilder(
    application,
    MyDatabase::class.java, "database-name"
).allowMainThreadQueries().fallbackToDestructiveMigration().build()

fun provideCollectionDao(postDataBase: MyDatabase) = postDataBase.collectionDao()
fun provideConnectionDao(postDataBase: MyDatabase) = postDataBase.connectionDao()
fun provideNoteDao(postDataBase: MyDatabase) = postDataBase.noteDao()

val dataModule = module{
    single<CollectionRepository> {
        CollectionRepositoryImpl(get())
    }

    single<NoteRepository> {
        NoteRepositoryImpl(get())
    }

    single<ConnectionRepository> {
        ConnectionRepositoryImpl(get())
    }

    single { provideDataBase(get()) }
    single { provideCollectionDao(get()) }
    single { provideConnectionDao(get()) }
    single { provideNoteDao(get()) }
}