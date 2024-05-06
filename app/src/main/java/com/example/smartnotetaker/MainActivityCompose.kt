package com.example.smartnotetaker

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.smartnotetaker.ui.theme.SmartNoteTakerTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.data.MyDatabase
import com.example.data.dao.CollectionDao
import com.example.data.dao.ConnectionDao
import com.example.data.dao.NoteDAO
import com.example.data.repositoryimplementation.CollectionRepositoryImpl
import com.example.data.repositoryimplementation.NoteRepositoryImpl
import com.example.domain.models.Note
import com.example.domain.usecase.CreateCollectionUseCase
import com.example.domain.usecase.CreateNoteUseCase
import com.example.domain.usecase.DeleteAllNotes
import com.example.domain.usecase.GetAllNotesUseCase
import com.example.domain.usecase.ViewCollectionsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class MainActivityCompose : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = Room.databaseBuilder(
            this,
            MyDatabase::class.java, "database-name"
        ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
        enableEdgeToEdge()
        setContent {
            SmartNoteTakerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
                    MainScreen(modifier = Modifier.padding(innerPadding), db)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SmartNoteTakerTheme {
        Greeting("Android")
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier, db: MyDatabase) {
    val scope = rememberCoroutineScope()
    var noteName by remember { mutableStateOf("") }
    var noteText by remember { mutableStateOf("") }
    var noteCollection by remember { mutableStateOf("") }
    var collectionName by remember { mutableStateOf("") }

    val noteDao: NoteDAO = db.noteDao()
    val collectionDao: CollectionDao = db.collectionDao()
    val connectionDao: ConnectionDao = db.connectionDao()

    val createNoteUseCase = CreateNoteUseCase(NoteRepositoryImpl(noteDao))
    val createCollectionUseCase = CreateCollectionUseCase(CollectionRepositoryImpl(collectionDao))
    val showNoteUseCase = GetAllNotesUseCase(NoteRepositoryImpl(noteDao))
    val showAllCollections = ViewCollectionsUseCase(CollectionRepositoryImpl(collectionDao))
    val deleteAllNotesUseCase = DeleteAllNotes(NoteRepositoryImpl(noteDao))


    var noteList by remember { mutableStateOf<List<Note>>(listOf()) }

    Column(modifier = Modifier.fillMaxSize()) {
        Column(Modifier.weight(1f)) { // Create two sections with weights
            OutlinedTextField(
                value = noteName,
                onValueChange = { noteName = it },
                label = { Text("Note Name") }
            )
            OutlinedTextField(
                value = noteText,
                onValueChange = { noteText = it },
                label = { Text("Note Text") }
            )
            OutlinedTextField(
                value = noteCollection,
                onValueChange = { noteCollection = it },
                label = { Text("Collection ID") }
            )
            Row(modifier = modifier) {
                Button(onClick = {
                    scope.launch {
                        createNoteUseCase(
                            Note(
                                name = noteName,
                                text = noteText,
                                collectionId = noteCollection.toLong()
                            )
                        )
                    }
                }) {
                    Text("Save Note")
                }
                Button(onClick = {
                    scope.launch {
                        deleteAllNotesUseCase()
                    }
                }) {
                    Text("Delete Notes")
                }
            }
        }
        Button(onClick = {
            scope.launch {
                noteList = showNoteUseCase() // Оновлюємо список нотаток
            }
        }) {
            Text("Show all notes")
        }
        Text(text = "Your Notes") // Display note or collection information here
        LazyColumn {
            items(noteList) { note ->
                Text(text = "Title: ${note.name}")
                Text(text = "Description: ${note.text}")
                Divider(modifier = Modifier.padding(vertical = 8.dp))
            }
        }
        Column(Modifier.weight(1f)) { // Create two sections with weights
            OutlinedTextField(
                value = collectionName,
                onValueChange = { collectionName = it },
                label = { Text("Collection Name") }
            )
            Row(modifier = modifier) {
                Button(onClick = { }) {
                    Text("Delete all collections")
                }
                Button(onClick = { }) {
                    Text("Save Collection")
                }
            }
        }
        Button(onClick = { }) {
            Text("Show all collections")
        }
    }
}
