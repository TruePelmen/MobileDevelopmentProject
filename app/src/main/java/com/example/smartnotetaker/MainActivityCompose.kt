package com.example.smartnotetaker

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.domain.models.Collection
import com.example.domain.models.Note
import com.example.smartnotetaker.di.appModule
import com.example.smartnotetaker.di.dataModule
import com.example.smartnotetaker.di.domainModule
import com.example.smartnotetaker.ui.theme.SmartNoteTakerTheme
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MainActivityCompose : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startKoin {
            androidContext(application)
            // Вказати список модулів для ініціалізації
            modules(domainModule, dataModule, appModule)
        }
        // Отримайте ViewModel з Koin
        val viewModel: MainViewModel = get<MainViewModel>()
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            SmartNoteTakerTheme {
                Scaffold(modifier = Modifier.padding(0.dp)) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "Collections",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("Collections") {
                            CollectionsScreen(
                                viewModel = viewModel,
                                navController = navController,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        composable("AddCollection") {
                            AddCollectionScreen(navController = navController, viewModel = viewModel)
                        }
                        composable("EditCollection/{collectionId}") { backStackEntry ->
                            val collectionId = backStackEntry.arguments?.getString("collectionId") ?: return@composable
                            EditCollectionScreen(collectionId, navController, viewModel)
                        }
                        composable("DeleteCollectionDialog/{collectionId}"){backStackEntry ->
                            val collectionId = backStackEntry.arguments?.getString("collectionId") ?: return@composable
                            DeleteCollectionDialog(
                                collectionId = collectionId,
                                onDismissRequest = { navController.popBackStack() },
                                onConfirmation = { viewModel.getCollectionById(collectionId)
                                    ?.let { viewModel.deleteCollection(it)
                                    viewModel.loadCollections()}
                                    navController.popBackStack()},
                                dialogTitle = "Deleting the collection",
                                dialogText = "Are you sure you want to delete ${viewModel.getCollectionById(collectionId)}",
                                icon = Icons.Default.Info
                            )
                        }
                        composable("Notes/{collectionId}") { backStackEntry ->
                            val collectionId = backStackEntry.arguments?.getString("collectionId") ?: return@composable
                            NotesScreen(collectionId, navController, viewModel)
                        }
                        composable("AddNote/{collectionId}"){backStackEntry ->
                            val collectionId = backStackEntry.arguments?.getString("collectionId") ?: return@composable
                            AddNoteScreen(collectionId.toLong(), navController, viewModel)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CollectionsScreen(
    viewModel: MainViewModel,
    navController: NavController,
    modifier: Modifier
) {
    val collections = viewModel.getCollections()
    Column(modifier = modifier) {
        Row{
            Text(text = "Collections", style = MaterialTheme.typography.headlineLarge)

        }

        Spacer(modifier = Modifier.height(16.dp))
        if (collections.isEmpty()) {
            Text(text = "No collections yet.", style = MaterialTheme.typography.bodyMedium)
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(collections) { collection ->
                    CollectionItem(collection, onCollectionClicked = {
                        navController.navigate("Notes/${collection.id}")
                    }, onEditClicked = {
                        navController.navigate("EditCollection/${collection.id}")
                    },
                        onDeleteClicked = {
                            navController.navigate("DeleteCollectionDialog/${collection.id}")
                        }
                    )
                }
            }
            IconButton(onClick = { navController.navigate("AddCollection") }) {
                Icon(Icons.Filled.AddCircle, contentDescription = "Add new collection")
            }
        }
    }
}

@Composable
fun CollectionItem(
    collection: Collection,
    onCollectionClicked: (Collection) -> Unit,
    onEditClicked: () -> Unit,
    onDeleteClicked: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Surface(modifier = Modifier.padding(8.dp), color = Color.Transparent) {
            Row(modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)) {
                Text(text = collection.name, style = MaterialTheme.typography.bodyMedium, )
                Spacer(modifier = Modifier.height(8.dp))
                IconButton(onClick = { onCollectionClicked(collection)}) {
                    Icon(Icons.Filled.List, contentDescription = "View notes from this collection")
                }
                IconButton(onClick = { onEditClicked()}) {
                    Icon(Icons.Filled.Edit, contentDescription = "Edit this collection")
                }
                IconButton(onClick = { onDeleteClicked()}) {
                    Icon(Icons.Filled.Delete, contentDescription = "Delete this collection")
                }
           }
        }
    }
}


@Composable
fun AddCollectionScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    Log.d("Navigation", "AddCollectionScreen has started")

    var collectionName by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = collectionName,
            onValueChange = { newName -> collectionName = newName },
            label = { Text("Collection Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            if (collectionName.isNotBlank()) {
                viewModel.addCollection(collectionName)
                navController.popBackStack()
            }
        }) {
            Text(text = "Create Collection")
        }
    }
}

@Composable
fun EditCollectionScreen(
    collectionId: String,
    navController: NavController,
    viewModel: MainViewModel
) {
    val collection = viewModel.getCollectionById(collectionId)
    var collectionName by remember { mutableStateOf(collection?.name ?: "") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = collectionName,
            onValueChange = { newName -> collectionName = newName },
            label = { Text("Collection Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            if (collection != null) {
                collection.name = collectionName
                viewModel.editCollection(collection)
                navController.popBackStack()
            }
        }) {
            Text(text = "Save Changes")
        }
    }
}


@Composable
fun DeleteCollectionDialog(
    collectionId: String,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun NotesScreen(
    collectionId: String,
    navController: NavController,
    viewModel: MainViewModel
) {
    viewModel.getNotesForCollection(collectionId)
    val notes = viewModel.getNotesUiState()

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Notes", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))
        if (notes.isEmpty()) {
            Text(text = "No notes in this collection.", style = MaterialTheme.typography.bodyMedium)
        } else {
            LazyColumn {
                items(notes) { note ->
                    NoteItem(note = note)
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        IconButton(onClick = { navController.navigate("AddNote/${collectionId}") }) {
            Icon(Icons.Filled.AddCircle, contentDescription = "Add new note")
        }
    }
}

@Composable
fun NoteItem(note: Note) {
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = note.name, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = note.text, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun AddNoteScreen(
    collectionId: Long,
    navController: NavController,
    viewModel: MainViewModel
) {
    Log.d("Navigation", "AddCollectionScreen has started")

    var noteName by remember { mutableStateOf("") }
    var noteText by remember { mutableStateOf("") }
    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            maxLines = 1,
            value = noteName,
            onValueChange = { newName -> noteName = newName },
            label = { Text("Note Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))
        TextField(
            value = noteText,
            onValueChange = { newText -> noteText = newText },
            label = { Text("Note Text") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = {
            if (noteName.isNotBlank()) {
                val note = Note(name = noteName, text = noteText, collectionId = collectionId)
                viewModel.addNote(note, collectionId.toString())
                navController.popBackStack()
            }
        }) {
            Text(text = "Create Note")
        }
    }
}