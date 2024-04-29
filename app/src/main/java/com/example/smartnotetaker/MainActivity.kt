package com.example.smartnotetaker

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.data.dao.CollectionDao
import com.example.data.MyDatabase
import com.example.data.dao.ConnectionDao
import com.example.data.dao.NoteDAO
import com.example.smartnotetaker.databinding.ActivityMainBinding
import com.example.data.repositoryimplementation.CollectionRepositoryImpl
import com.example.data.repositoryimplementation.ConnectionRepositoryImpl
import com.example.data.repositoryimplementation.NoteRepositoryImpl
import com.example.domain.models.Note
import com.example.domain.models.Collection
import com.example.domain.usecase.CreateCollectionUseCase
import com.example.domain.usecase.CreateNoteUseCase
import com.example.domain.usecase.DeleteAllNotes
import com.example.domain.usecase.DeleteCollectionUseCase
import com.example.domain.usecase.GetAllNotesUseCase
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.sql.Date

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val db = Room.databaseBuilder(
            applicationContext,
            MyDatabase::class.java, "database-name"
        ).allowMainThreadQueries().fallbackToDestructiveMigration().build()

        val noteDao: NoteDAO = db.noteDao()
        val collectionDao: CollectionDao = db.collectionDao()
        val connectionDao: ConnectionDao = db.connectionDao()

        val createNoteUseCase = CreateNoteUseCase(NoteRepositoryImpl(noteDao))
        val createCollectionUseCase = CreateCollectionUseCase(CollectionRepositoryImpl(collectionDao))
        val showNoteUseCase = GetAllNotesUseCase(NoteRepositoryImpl(noteDao))
        val deleteAllNotesUseCase = DeleteAllNotes(NoteRepositoryImpl(noteDao))
        val deleteAllCollectionUseCase = DeleteCollectionUseCase(CollectionRepositoryImpl(collectionDao), NoteRepositoryImpl(noteDao), ConnectionRepositoryImpl(connectionDao))


        binding.btnSaveNote.setOnClickListener{
            val note = Note(name = binding.etNoteName.text.toString(), text = binding.etNoteText.text.toString(), collectionId = binding.etNoteCollection.text.toString().toLong())

            lifecycleScope.launch {
                createNoteUseCase.invoke(note)
            }
        }
        binding.btnSaveCollection.setOnClickListener{
            val collection= Collection(name = binding.etCollectionName.text.toString())

            lifecycleScope.launch {
                createCollectionUseCase.invoke(collection)
            }
        }

        binding.btnShowNote.setOnClickListener{
            binding.tvNotes.text = ""

            var noteCount = ""
            var noteList: List<Note> = listOf()
            lifecycleScope.launch{
                noteCount = "NoteCount ${showNoteUseCase.invoke().count()}"
                noteList = showNoteUseCase.invoke()
            }
            binding.tvNotes.text = noteCount
            for (i in noteList) {
                binding.tvNotes.text = buildString {
                    append(binding.tvNotes.text.toString())
                    append("\n")
                    append(i.name)
                    append("\t")
                    append(i.text)
                    append("\t")
                    append(i.creationDate)
                    append("\t")
                    append("Collection: ${i.collectionId}")
                }
            }
        }

        binding.btnShowCollection.setOnClickListener{
            binding.tvNotes.text = ""
            val collectionCount = "CollectionCount ${collectionDao.getAllCollections().count()}"
            val collectionList = collectionDao.getAllCollections()
            binding.tvNotes.text = collectionCount
            for (i in collectionList) {
                binding.tvNotes.text = buildString {
                    append(binding.tvNotes.text.toString())
                    append("\n")
                    append(i.id)
                    append("\t")
                    append(i.name)
                    append("\t")
                    append(i.creationDate)
                    append("\t")
                }
            }
        }

        binding.btnDeleteNote.setOnClickListener{
            lifecycleScope.launch {
                deleteAllNotesUseCase.invoke()
            }

        }
        binding.btnDeleteCollection.setOnClickListener{
            collectionDao.deleteAllCollections()
        }

        }
}