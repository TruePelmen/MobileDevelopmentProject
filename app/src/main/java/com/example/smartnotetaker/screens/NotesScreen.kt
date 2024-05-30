package com.example.smartnotetaker.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.smartnotetaker.MainViewModel

@Composable
fun NotesScreen(
    collectionId: String,
    navController: NavController,
    viewModel: MainViewModel
) {
    LaunchedEffect(collectionId) {
        viewModel.getNotesForCollection(collectionId)
    }
    val notes = viewModel.getNotesUiState()
    val collection = viewModel.getCollectionById(collectionId)

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()) {
            if (collection != null) {
                Row {
                    Text(text = collection.name, style = MaterialTheme.typography.headlineLarge)
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = collection.creationDate.toString(), style = MaterialTheme.typography.titleSmall)
                }

            }
            Spacer(modifier = Modifier.height(16.dp))
            if (notes.isEmpty()) {
                Text(text = "No notes in this collection.", style = MaterialTheme.typography.bodyMedium)
            } else {
                NoteList(
                    notes = notes,
                    navController
                )
            }
        }
        FloatingActionButton(
            onClick = { navController.navigate("AddNote/$collectionId") },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Filled.AddCircle, contentDescription = "Add new note")
        }
    }
}