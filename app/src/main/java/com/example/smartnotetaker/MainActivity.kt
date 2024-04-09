package com.example.smartnotetaker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.smartnotetaker.data.dao.CollectionDao
import com.example.smartnotetaker.data.MyDatabase
import com.example.smartnotetaker.data.dao.NoteDAO
import com.example.smartnotetaker.databinding.ActivityMainBinding
import com.example.smartnotetaker.data.entities.CollectionEntity
import com.example.smartnotetaker.data.entities.NoteEntity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
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

        binding.btnSaveNote.setOnClickListener{
            val noteEntity = NoteEntity(name = binding.etNoteName.text.toString(), text = binding.etNoteText.text.toString(), collectionId = binding.etNoteCollection.text.toString().toLong())
            noteDao.insert(noteEntity)
        }
        binding.btnSaveCollection.setOnClickListener{
            val collectionEntity = CollectionEntity(name = binding.etCollectionName.text.toString())
            collectionDao.insertCollection(collectionEntity)
        }

        binding.btnShowNote.setOnClickListener{
            binding.tvNotes.text = ""
            val noteCount = "NoteCount ${noteDao.getAll().count()}"
            val noteList = noteDao.getAll()
            binding.tvNotes.text = noteCount
            for (i in noteList) {
                binding.tvNotes.text = buildString {
                    append(binding.tvNotes.text.toString())
                    append("\n")
                    append(i.name)
                    append("\t")
                    append(i.text)
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
                }
            }
        }

        binding.btnDeleteNote.setOnClickListener{
            noteDao.deleteAll()
        }
        binding.btnDeleteCollection.setOnClickListener{
            collectionDao.deleteAllCollections()
        }

        }
}